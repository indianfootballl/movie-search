#!/bin/bash

# Railway Deployment Script for Movie Search AI
# This script helps deploy both backend and frontend to Railway

set -e

echo "🚀 Starting Railway Deployment for Movie Search AI"
echo "=================================================="

# Check if Railway CLI is installed
if ! command -v railway &> /dev/null; then
    echo "❌ Railway CLI is not installed. Please install it first:"
    echo "   brew install railway"
    exit 1
fi

# Check if user is logged in
if ! railway whoami &> /dev/null; then
    echo "❌ Please login to Railway first:"
    echo "   railway login"
    exit 1
fi

echo "✅ Railway CLI is ready"

# Function to deploy backend
deploy_backend() {
    echo ""
    echo "📦 Deploying Backend Service..."
    echo "==============================="
    
    cd backend
    
    # Initialize or link Railway project
    if [ ! -f "railway.json" ]; then
        echo "Initializing Railway project for backend..."
        railway init --name movie-search-backend
    else
        echo "Linking to existing Railway project..."
        railway link
    fi
    
    # Set environment variables
    echo "Setting environment variables..."
    echo "YOUTUBE_API_KEY=AIzaSyBAcoRQ1ceHTH2vSpz5C10PesXN9Mt8WUI" | railway variables
    echo "CORS_ALLOWED_ORIGINS=http://localhost:3000" | railway variables
    
    # Deploy
    echo "Deploying backend..."
    railway up
    
    # Get the backend URL
    echo "Getting backend URL..."
    BACKEND_URL=$(railway domain)
    echo "✅ Backend deployed at: $BACKEND_URL"
    
    cd ..
}

# Function to deploy frontend
deploy_frontend() {
    echo ""
    echo "🎨 Deploying Frontend Service..."
    echo "================================"
    
    cd frontend
    
    # Initialize Railway project if not already done
    if [ ! -f "railway.json" ]; then
        echo "Initializing Railway project for frontend..."
        railway init --name movie-search-frontend
    fi
    
    # Set environment variables
    echo "Setting environment variables..."
    railway variables set REACT_APP_API_URL=https://$BACKEND_URL/api
    
    # Deploy
    echo "Deploying frontend..."
    railway up
    
    # Get the frontend URL
    echo "Getting frontend URL..."
    FRONTEND_URL=$(railway domain)
    echo "✅ Frontend deployed at: $FRONTEND_URL"
    
    cd ..
}

# Function to update CORS
update_cors() {
    echo ""
    echo "🔧 Updating CORS Configuration..."
    echo "================================="
    
    cd backend
    railway variables set CORS_ALLOWED_ORIGINS=https://$FRONTEND_URL
    railway up
    cd ..
    
    echo "✅ CORS configuration updated"
}

# Main deployment flow
main() {
    # Deploy backend first
    deploy_backend
    
    # Deploy frontend
    deploy_frontend
    
    # Update CORS configuration
    update_cors
    
    echo ""
    echo "🎉 Deployment Complete!"
    echo "======================"
    echo "Frontend URL: https://$FRONTEND_URL"
    echo "Backend URL: https://$BACKEND_URL"
    echo ""
    echo "📝 Next Steps:"
    echo "1. Visit your frontend URL to test the application"
    echo "2. Check Railway dashboard for monitoring"
    echo "3. Update your YouTube API key if needed"
    echo ""
    echo "🔍 To view logs: railway logs"
    echo "📊 To check status: railway status"
}

# Run main function
main

