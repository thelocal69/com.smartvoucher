# build stage
FROM node:18-alpine as smartvoucher-home
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build


# production stage
FROM nginx:latest
COPY --from=smartvoucher-home /app/build /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]