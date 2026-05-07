# Admin API Endpoints - Postman Curl Commands

## Applications

### GET All Applications
```bash
curl --location 'http://localhost:4567/api/admin/applications' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN'
```

### GET Application by ID
```bash
curl --location 'http://localhost:4567/api/admin/applications/1' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN'
```

### POST Create Application
```bash
curl --location 'http://localhost:4567/api/admin/applications' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN' \
--data '{
  "name": "Insurance Portal",
  "description": "Main insurance application portal"
}'
```

## Environments

### GET All Environments
```bash
curl --location 'http://localhost:4567/api/admin/environments' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN'
```

### GET Environment by ID
```bash
curl --location 'http://localhost:4567/api/admin/environments/1' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN'
```

### POST Create Environment
```bash
curl --location 'http://localhost:4567/api/admin/environments' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN' \
--data '{
  "name": "PRODUCTION",
  "region": "US-East"
}'
```

## Sections

### GET All Sections
```bash
curl --location 'http://localhost:4567/api/admin/sections' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN'
```

### GET Section by ID
```bash
curl --location 'http://localhost:4567/api/admin/sections/1' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN'
```

### POST Create Section
```bash
curl --location 'http://localhost:4567/api/admin/sections' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN' \
--data '{
  "name": "Policy Management"
}'
```

## URL Requests

### GET All URL Requests
```bash
curl --location 'http://localhost:4567/api/admin/url-requests' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN'
```

### GET URL Request by ID
```bash
curl --location 'http://localhost:4567/api/admin/url-requests/1' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN'
```

### POST Create URL Request
```bash
curl --location 'http://localhost:4567/api/admin/url-requests' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN' \
--data '{
  "baseUrl": "https://api.example.com/policies",
  "tile": "POLICY_SERVICE",
  "description": "Policy management service",
  "status": 200,
  "application": {"id": 1},
  "environment": {"id": 1},
  "section": {"id": 1}
}'
```

### POST Refresh URL Status
```bash
curl --location 'http://localhost:4567/api/admin/url-requests/{id}/refresh-status' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN' \
--header 'Content-Type: application/json'
```

## Parallel URL Testing

### GET Fetch URLs
```bash
curl --location 'http://localhost:4567/api/urls-parallel/fetch' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN'
```

### POST Call URLs in Parallel
```bash
curl --location 'http://localhost:4567/api/urls-parallel/call' \
--header 'Content-Type: application/json' \
--data '{
  "urls": [
    "https://api.example.com/policies",
    "https://api.example.com/users",
    "https://api.example.com/claims"
  ]
}'
```

## Authentication

### POST Login (if you have auth endpoint)
```bash
curl --location 'http://localhost:4567/api/auth/login' \
--header 'Content-Type: application/json' \
--data '{
  "username": "your_username",
  "password": "your_password"
}'
```

---

## Notes:
- Replace `YOUR_JWT_TOKEN` with your actual JWT token
- All POST requests require `Content-Type: application/json` header
- GET requests for specific IDs use the format `/endpoint/{id}`
- Environment creation now works after fixing the `@GeneratedValue` annotation
