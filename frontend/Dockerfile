FROM node:18-alpine

WORKDIR /app

# Optional build-time API URL
ARG REACT_APP_API_URL
ENV REACT_APP_API_URL=${REACT_APP_API_URL}

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy source code
COPY . .

# Build the application (picks up REACT_APP_API_URL)
RUN npm run build

# Install serve globally
RUN npm install -g serve

EXPOSE 3000

# Use serve to serve the built application
CMD ["serve", "-s", "build", "-l", "3000"]
