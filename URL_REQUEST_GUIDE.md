# URL Request API Guide

## Creating a URL Request - Correct Format

The `/api/admin/url-requests` POST endpoint expects a properly structured JSON object with nested relationship objects, not IDs.

### ✅ CORRECT Request Format

```bash
curl --location 'http://localhost:4567/api/admin/url-requests' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN' \
--data '{
  "baseUrl": "https://api.uat.dummy-insurance.com",
  "tile": "DUMMY_POLICY_DASHBOARD",
  "description": "Dummy policy management dashboard for testing",
  "application": {
    "id": 1
  },
  "environment": {
    "id": 1
  },
  "section": {
    "id": 1
  }
}'
```

### Expected Response (201 Created)

```json
{
  "id": 1,
  "baseUrl": "https://api.uat.dummy-insurance.com",
  "tile": "DUMMY_POLICY_DASHBOARD",
  "description": "Dummy policy management dashboard for testing",
  "applicationId": 1,
  "applicationName": "Insurance App",
  "environmentId": 1,
  "environmentName": "UAT",
  "sectionId": 1,
  "sectionName": "Policy Services",
  "status": null,
  "body": null
}
```

---

## Common Issues & Solutions

### ❌ Issue 1: Using ID fields instead of relationship objects

**Wrong:**
```json
{
  "applicationId": 9999,
  "environmentId": 99,
  "sectionId": 88
}
```

**Correct:**
```json
{
  "application": {"id": 1},
  "environment": {"id": 1},
  "section": {"id": 1}
}
```

### ❌ Issue 2: Nested URL arrays not supported in entity

**Wrong:**
```json
{
  "urls": [
    {"url": "https://api.uat.dummy-insurance.com/policies"},
    {"url": "https://api.uat.dummy-insurance.com/policies/active"}
  ]
}
```

**Note:** The `urls` field is `@Transient` and used only for the parallel URL calling feature (different endpoint: `/api/urls-parallel/call`)

### ❌ Issue 3: Including unnecessary fields

**Wrong:**
```json
{
  "id": 999,
  "status": 1,
  "body": "dummy-policy-service-v1",
  "applicationId": 9999,
  "environmentId": 99,
  "sectionId": 88
}
```

- `id`: Should not be included in POST (auto-generated)
- `status`, `body`: Response-only fields
- `applicationId`, `environmentId`, `sectionId`: Use nested objects instead

---

## Creating Multiple URLs

If you want to create multiple URL requests, make separate POST calls:

```bash
# Create first URL
curl -X POST http://localhost:4567/api/admin/url-requests \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "baseUrl": "https://api.uat.dummy-insurance.com/policies",
    "tile": "POLICIES",
    "description": "Policy API endpoint",
    "application": {"id": 1},
    "environment": {"id": 1},
    "section": {"id": 1}
  }'

# Create second URL
curl -X POST http://localhost:4567/api/admin/url-requests \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "baseUrl": "https://api.uat.dummy-insurance.com/policies/active",
    "tile": "ACTIVE_POLICIES",
    "description": "Active Policies endpoint",
    "application": {"id": 1},
    "environment": {"id": 1},
    "section": {"id": 1}
  }'
```

---

## Testing Multiple URLs (Parallel Call)

Use the parallel endpoint for testing multiple URLs at once:

```bash
curl -X POST http://localhost:4567/api/urls-parallel/call \
  -H "Content-Type: application/json" \
  -d '{
    "urls": [
      "https://api.uat.dummy-insurance.com/policies",
      "https://api.uat.dummy-insurance.com/policies/active",
      "https://api.uat.dummy-insurance.com/policies/{policyId}"
    ]
  }'
```

This endpoint accepts the `urls` array with string URLs and returns their status codes and response bodies.

---

## Entity Field Constraints

| Field | Type | Required | Notes |
|-------|------|----------|-------|
| baseUrl | String | ✓ | Must be a valid URL |
| tile | String | ✓ | Unique across all URL requests |
| description | String | | Optional |
| application | Object | | Must have valid ID |
| environment | Object | | Must have valid ID |
| section | Object | | Must have valid ID |

---

## Troubleshooting

### 401 Unauthorized
- Missing or invalid JWT token in Authorization header
- Token may have expired

### 400 Bad Request
- Check JSON syntax
- Verify field names match the expected format
- Ensure nested objects have proper structure

### 409 Conflict
- `tile` value already exists (must be unique)
- Try with a different tile name

### 404 Not Found
- Referenced application, environment, or section IDs don't exist
- Create these entities first before linking them

