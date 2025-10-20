# Railway Deployment Guide

This guide will help you deploy your Movie Search AI application to Railway with both backend and frontend services.

## Prerequisites

1. ✅ Railway CLI installed (already done)
2. ✅ Railway account created
3. ✅ Logged into Railway CLI

## Step 1: Login to Railway

Run this command in your terminal:
```bash
railway login
```

This will open a browser window for authentication.

## Step 2: Deploy Backend Service

### 2.1 Initialize Railway Project for Backend

```bash
cd backend
railway init
```

When prompted:
- Choose "Create a new project"
- Enter project name: `movie-search-backend`

### 2.2 Set Environment Variables

Set the required environment variables:

```bash
# Set YouTube API Key (replace with your actual key)
railway variables set YOUTUBE_API_KEY=AIzaSyBAcoRQ1ceHTH2vSpz5C10PesXN9Mt8WUI

# Set CORS allowed origins (will be updated after frontend deployment)
railway variables set CORS_ALLOWED_ORIGINS=http://localhost:3000
```

### 2.3 Deploy Backend

```bash
railway up
```

### 2.4 Get Backend URL

After deployment, get your backend URL:
```bash
railway domain
```

Save this URL - you'll need it for the frontend configuration.

## Step 3: Deploy Frontend Service

### 3.1 Initialize Railway Project for Frontend

```bash
cd ../frontend
railway init
```

When prompted:
- Choose "Create a new project"
- Enter project name: `movie-search-frontend`

### 3.2 Set Environment Variables

Set the frontend environment variables:

```bash
# Set the backend API URL (replace with your actual backend URL)
railway variables set REACT_APP_API_URL=https://your-backend-service.railway.app/api
```

### 3.3 Deploy Frontend

```bash
railway up
```

### 3.4 Get Frontend URL

After deployment, get your frontend URL:
```bash
railway domain
```

## Step 4: Update CORS Configuration

### 4.1 Update Backend CORS

Go back to the backend directory and update the CORS configuration:

```bash
cd ../backend

# Update CORS with your frontend URL (replace with your actual frontend URL)
railway variables set CORS_ALLOWED_ORIGINS=https://your-frontend-service.railway.app
```

### 4.2 Redeploy Backend

```bash
railway up
```

## Step 5: Test Your Deployment

1. Visit your frontend URL
2. Test the movie search functionality
3. Verify that the backend API is accessible

## Environment Variables Summary

### Backend Environment Variables:
- `YOUTUBE_API_KEY`: Your YouTube Data API v3 key
- `CORS_ALLOWED_ORIGINS`: Frontend URL for CORS
- `PORT`: Automatically set by Railway

### Frontend Environment Variables:
- `REACT_APP_API_URL`: Backend API URL

## Troubleshooting

### Common Issues:

1. **CORS Errors**: Make sure `CORS_ALLOWED_ORIGINS` is set to your frontend URL
2. **API Key Issues**: Verify your YouTube API key is correct and has proper permissions
3. **Build Failures**: Check Railway logs for specific error messages

### View Logs:
```bash
railway logs
```

### Check Service Status:
```bash
railway status
```

## Custom Domains (Optional)

If you want to use custom domains:

1. Go to Railway dashboard
2. Select your service
3. Go to Settings → Domains
4. Add your custom domain
5. Update DNS records as instructed

## Monitoring

Railway provides built-in monitoring:
- View metrics in the Railway dashboard
- Set up alerts for service health
- Monitor resource usage

## Scaling

Railway automatically handles:
- Load balancing
- Auto-scaling based on traffic
- Health checks and restarts

## Cost Optimization

- Railway offers a free tier with limited resources
- Monitor usage in the dashboard
- Upgrade plans as needed for production use

## Security Notes

- Never commit API keys to version control
- Use Railway's environment variables for sensitive data
- Regularly rotate API keys
- Monitor API usage to avoid quota limits

## Next Steps

1. Set up monitoring and alerts
2. Configure custom domains if needed
3. Set up CI/CD for automatic deployments
4. Implement proper logging and error handling
5. Consider adding authentication if needed

