# CORS Configuration Fix - Complete Guide

## Problem
You were getting this error:
```
Access to fetch at 'https://vm-service-slide-1.onrender.com/api/admin/url-requests' 
from origin 'http://localhost:5173' has been blocked by CORS policy: 
Response to preflight request doesn't pass access control check: 
No 'Access-Control-Allow-Origin' header is present on the requested resource.
```

## Root Causes Fixed

### 1. **Incorrect CORS Origin Patterns**
**Problem:** Using simple URLs instead of regex patterns with `allowedOriginPatterns`
```java
// ❌ WRONG - These are treated as literal strings, not patterns
.allowedOriginPatterns(
    "http://localhost:3000",
    "http://localhost:5173"
)
```

**Solution:** The strings in `allowedOriginPatterns` are already treated correctly if they're exact domain matches. We added regex support for wildcard domains:
```java
// ✅ CORRECT - Supports exact matches and wildcard patterns
.allowedOriginPatterns(
    "http://localhost:3000",           // Exact match
    "http://localhost:5173",           // Exact match
    "http://localhost:5174",           // Exact match
    "https://vm-service-slide-1.onrender.com",  // Exact match
    "https://.*\\.onrender\\.com"      // Regex for any subdomain on onrender.com
)
```

### 2. **Security Filter Blocking CORS Preflight Requests**
**Problem:** SecurityConfig was requiring authentication for all requests, including OPTIONS preflight
```java
// ❌ WRONG - Requires authentication for everything
.authorizeHttpRequests(authz -> authz
    .requestMatchers("/api/auth/**").permitAll()
    .requestMatchers("/api/admin/**").authenticated()
    .anyRequest().authenticated()  // This blocks OPTIONS requests!
)
```

**Solution:** Explicitly allow OPTIONS requests without authentication (first rule wins):
```java
// ✅ CORRECT - OPTIONS requests allowed first
.authorizeHttpRequests(authz -> authz
    .requestMatchers("OPTIONS").permitAll()  // CORS preflight - must be first!
    .requestMatchers("/api/auth/**").permitAll()
    .requestMatchers("/api/admin/**").authenticated()
    .anyRequest().authenticated()
)
```

### 3. **Missing Path Coverage**
**Problem:** CORS config only covered `/api/**` and `/auth/**`, missing other endpoints
```java
// ❌ INCOMPLETE - Didn't cover all API paths
registry.addMapping("/api/**")
registry.addMapping("/auth/**")
```

**Solution:** Added comprehensive coverage for all API paths:
```java
// ✅ COMPLETE - All paths covered
registry.addMapping("/api/**")
registry.addMapping("/auth/**")
registry.addMapping("/urls-parallel/**")
registry.addMapping("/dashboard/**")
registry.addMapping("/status/**")
```

---

## Updated Configuration Files

### CorsConfig.java
```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // All endpoints with same CORS settings
                registry.addMapping("/api/**")
                registry.addMapping("/auth/**")
                registry.addMapping("/urls-parallel/**")
                registry.addMapping("/dashboard/**")
                registry.addMapping("/status/**")
                        .allowedOriginPatterns(
                            "http://localhost:3000",
                            "http://localhost:5173",
                            "http://localhost:5174",
                            "https://vm-service-slide-1.onrender.com",
                            "https://.*\\.onrender\\.com"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
```

### SecurityConfig.java
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                // OPTIONS MUST BE FIRST - handles CORS preflight
                .requestMatchers("OPTIONS").permitAll()
                // Public endpoints
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/urls-parallel/fetch").permitAll()
                .requestMatchers("/api/urls-parallel/call").permitAll()
                // Swagger docs
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                // Protected endpoints
                .requestMatchers("/api/admin/**").authenticated()
                .requestMatchers("/api/dashboard/**").authenticated()
                .requestMatchers("/api/status/**").authenticated()
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
```

---

## Testing CORS Configuration

### 1. Test LOCAL Frontend → Deployed Backend

```bash
# From http://localhost:5173, run in browser console:
fetch('https://vm-service-slide-1.onrender.com/api/status/all', {
    method: 'GET',
    headers: {
        'Authorization': 'Bearer YOUR_JWT_TOKEN'
    }
})
.then(res => res.json())
.then(data => console.log('Success:', data))
.catch(err => console.error('CORS Error:', err))
```

### 2. Check Preflight Request Headers

```bash
# Using curl to see response headers
curl -i -X OPTIONS 'https://vm-service-slide-1.onrender.com/api/admin/url-requests' \
  -H 'Origin: http://localhost:5173' \
  -H 'Access-Control-Request-Method: POST' \
  -H 'Access-Control-Request-Headers: Content-Type,Authorization'
```

Expected response should include:
```
Access-Control-Allow-Origin: http://localhost:5173
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, PATCH, OPTIONS
Access-Control-Allow-Headers: *
Access-Control-Allow-Credentials: true
```

### 3. Test with cURL

```bash
curl -i -X POST 'https://vm-service-slide-1.onrender.com/api/admin/url-requests' \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer YOUR_JWT_TOKEN' \
  -H 'Origin: http://localhost:5173' \
  -d '{
    "baseUrl": "https://example.com",
    "tile": "TEST_TILE",
    "description": "Test",
    "application": {"id": 1},
    "environment": {"id": 1},
    "section": {"id": 1}
  }'
```

---

## Key Takeaways

| Configuration | Before | After |
|---|---|---|
| **CORS Origins** | Only hardcoded dev URLs | Supports multiple domains + wildcard patterns |
| **OPTIONS Requests** | Blocked by security | Explicitly permitted |
| **API Path Coverage** | Partial (`/api/**` only) | Complete (all endpoints) |
| **Credentials** | Issues with wildcard | Properly configured with patterns |
| **Max Age** | N/A | 3600 seconds (1 hour) browser caching |

---

## Redeployment Steps

1. **Rebuild project:**
   ```bash
   mvn clean package
   ```

2. **Deploy to Render:**
   - Push changes to Git
   - Render will auto-redeploy

3. **Clear browser cache:**
   - Browser caches CORS preflight responses for `maxAge` seconds
   - Hard refresh: `Ctrl+Shift+Delete` (Windows) or `Cmd+Shift+Delete` (Mac)

4. **Test from frontend:**
   - Try the request from `localhost:5173` again
   - Check browser Network tab for CORS headers

---

## Troubleshooting

### Still getting CORS error?

1. **Check browser console for actual error:**
   - Look for specific origin/header mismatch

2. **Verify token is valid:**
   ```bash
   curl -X POST http://localhost:4567/api/admin/url-requests \
     -H "Authorization: Bearer YOUR_TOKEN"
   ```

3. **Test OPTIONS preflight directly:**
   ```bash
   curl -X OPTIONS http://localhost:4567/api/admin/url-requests -H "Origin: http://localhost:5173"
   ```

4. **Check if paths match:**
   - Request to `/api/urls-parallel/...` should match `/urls-parallel/**` or `/api/**`

### CORS headers not showing up?

- **Check** if CORS config bean is registered (look for log output on startup)
- **Rebuild** the application after config changes
- **Clear** browser cache (preflight responses are cached)
- **Restart** the backend server

---

## Additional Resources

- [MDN - CORS Guide](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS)
- [Spring - CORS Documentation](https://spring.io/guides/gs/rest-service-cors/)
- [Spring Security - CORS and CSRF](https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html)

