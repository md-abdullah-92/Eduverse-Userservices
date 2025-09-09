# Render Deployment Instructions

## Environment Variables to Set in Render Dashboard

When deploying to Render, you need to set the following environment variables in your Render service dashboard:

### Database Configuration
- `DATABASE_URL`: `[Your MySQL Database URL from Aiven]`
- `DB_USERNAME`: `[Your Database Username]`
- `DB_PASSWORD`: `[Your Database Password]`

### JWT Configuration
- `JWT_SECRET`: `[Your JWT Secret Key - generate a secure random string]`
- `JWT_EXPIRATION`: `2592000000`

### Email Configuration
- `SMTP_HOST`: `smtp.gmail.com`
- `SMTP_PORT`: `587`
- `SMTP_USERNAME`: `[Your Gmail Address]`
- `SMTP_PASSWORD`: `[Your Gmail App Password]`

### CORS Configuration
- `CORS_ORIGINS`: `http://localhost:3000,http://localhost:3001,https://your-frontend-domain.onrender.com`

### Logging
- `LOG_LEVEL`: `INFO`

## Deployment Steps

1. Push your code to GitHub (excluding sensitive data)
2. Connect your GitHub repository to Render
3. Use the `render.yaml` file for basic configuration
4. Manually add the environment variables listed above in Render Dashboard
5. Deploy the service

## Security Note
Never commit sensitive data like passwords, API keys, or database credentials to your repository. Always use environment variables through the hosting platform's dashboard.

## Required Environment Variables
Make sure to set these in your Render service environment variables:
- DATABASE_URL
- DB_USERNAME  
- DB_PASSWORD
- JWT_SECRET
- SMTP_USERNAME
- SMTP_PASSWORD
