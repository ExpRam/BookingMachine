import {createAxiosDateTransformer} from "axios-date-transformer";

export const API_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';
export const instance = createAxiosDateTransformer({
    baseURL: API_URL,
})