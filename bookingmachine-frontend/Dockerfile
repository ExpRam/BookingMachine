FROM node:alpine AS builder

ARG NGINX_PORT NGINX_HOST
ENV VITE_API_BASE_URL=http://${NGINX_HOST}:${NGINX_PORT}/api

WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install --frozen-lockfile

COPY . ./
RUN npm run build

FROM nginx:alpine AS production

COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE $PORT
CMD ["nginx", "-g", "daemon off;"]