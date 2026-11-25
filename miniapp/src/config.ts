export const config = {
 useMock: false,
 baseURL: (import.meta.env.VITE_EHR_BASE_URL || '').trim().replace(/\/+$/, ''),
 allowHttpLocal: import.meta.env.VITE_ALLOW_HTTP_LOCAL === 'true',
 timeout: 25000,
 maxFiles: 9,
 maxFileSize: 20 * 1024 * 1024,
}
