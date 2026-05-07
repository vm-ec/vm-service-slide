# Application Update & Get All - Postman Curl Commands

## Complete Update & Verify Workflow

### 1️⃣ **Create Application (Initial)**
```bash
curl --location 'http://localhost:4567/api/admin/applications' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN' \
--data '{
  "name": "Insurance Portal",
  "description": "Initial application portal"
}'
```

**Expected Response (201):**
```json
{
  "id": 1,
  "name": "Insurance Portal",
  "description": "Initial application portal",
  "sections": [],
  "urls": []
}
```

---

### 2️⃣ **Get All Applications (Before Update)**
```bash
curl --location 'http://localhost:4567/api/admin/applications' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN'
```

**Expected Response (200):**
```json
[
  {
    "id": 1,
    "name": "Insurance Portal",
    "description": "Initial application portal",
    "sections": [],
    "urls": []
  }
]
```

---

### 3️⃣ **Update Application (Change ID and Name)**
```bash
curl --location --request PUT 'http://localhost:4567/api/admin/applications/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN' \
--data '{
  "id": 1,
  "name": "Updated Insurance Portal v2",
  "description": "Updated description for insurance portal"
}'
```

**Expected Response (200):**
```json
{
  "id": 1,
  "name": "Updated Insurance Portal v2",
  "description": "Updated description for insurance portal",
  "sections": [],
  "urls": []
}
```

---

### 4️⃣ **Get All Applications (After Update) - VERIFY CHANGES**
```bash
curl --location 'http://localhost:4567/api/admin/applications' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN'
```

**Expected Response (200) - Should reflect the updated name:**
```json
[
  {
    "id": 1,
    "name": "Updated Insurance Portal v2",
    "description": "Updated description for insurance portal",
    "sections": [],
    "urls": []
  }
]
```

✅ **The changes from step 3 should be visible here!**

---

### 5️⃣ **Get Specific Application by ID (Verify Changes)**
```bash
curl --location 'http://localhost:4567/api/admin/applications/1' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN'
```

**Expected Response (200):**
```json
{
  "id": 1,
  "name": "Updated Insurance Portal v2",
  "description": "Updated description for insurance portal",
  "sections": [],
  "urls": []
}
```

---

## Advanced: Update Only Name (Keep Description)

```bash
curl --location --request PUT 'http://localhost:4567/api/admin/applications/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN' \
--data '{
  "name": "Insurance Platform - Final Version",
  "description": "Updated description for insurance portal"
}'
```

---

## Advanced: Update Only Description (Keep Name)

```bash
curl --location --request PUT 'http://localhost:4567/api/admin/applications/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer YOUR_JWT_TOKEN' \
--data '{
  "name": "Updated Insurance Portal v2",
  "description": "New updated description only"
}'
```

---

## Testing Workflow Summary

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Create application | Application ID 1 with name "Insurance Portal" |
| 2 | Get all applications | Should list ID 1 with original name |
| 3 | Update application ID 1 with new name | Returns updated record with new name |
| 4 | **Get all applications** | **Should reflect new name from step 3** ✅ |
| 5 | Get by ID | Should return updated record with new name |

---

## Key Points for Success

✅ **The update logic is working correctly:**
- ID remains the same (ID = 1 in example)
- Name updates to the new value
- Changes persist to the database
- `GET /api/admin/applications` will show the updated name
- Full application object is returned after update

✅ **New @Transactional annotation:**
- Ensures database transaction commits properly
- Added at both class and method level
- Validates non-empty values before updating

✅ **Logging added:**
- Console will show: "✅ Application updated successfully - ID: 1, Name: Updated Insurance Portal v2"

---

## Troubleshooting

### Issue: Changes not reflecting in GET ALL
**Solution:** 
- Ensure you're using the correct **ID** in the PUT URL (`/applications/{id}`)
- Verify the JWT token is valid
- Check server logs for "✅ Application updated successfully" message

### Issue: 404 Not Found on Update
**Solution:**
- Verify the application ID exists
- Check that you're using the correct base URL (http://localhost:4567)

### Issue: 400 Bad Request
**Solution:**
- Ensure JSON content is properly formatted
- Provide at least the `name` field in the request body
- Verify `Content-Type: application/json` header is set

