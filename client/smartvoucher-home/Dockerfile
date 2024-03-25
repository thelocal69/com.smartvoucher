# build stage
FROM node:18-alpine as build-stage
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build

# production stage
FROM nginx:1.17-alpine as production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]