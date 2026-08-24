const ACCESS_TOKEN_KEY = 'tarot.accessToken';
const REFRESH_TOKEN_KEY = 'tarot.refreshToken';
const USER_KEY = 'tarot.user';

async function postJson(url, body) {
  const resp = await fetch(url, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(body) });
  const json = await resp.json();
  if (json.code !== '0000') throw new Error(json.message || '请求失败');
  return json.data;
}

export function saveAuth(auth) {
  localStorage.setItem(ACCESS_TOKEN_KEY, auth.accessToken);
  localStorage.setItem(REFRESH_TOKEN_KEY, auth.refreshToken);
  localStorage.setItem(USER_KEY, JSON.stringify({ userId: auth.userId, email: auth.email }));
}
export function clearAuth() { localStorage.removeItem(ACCESS_TOKEN_KEY); localStorage.removeItem(REFRESH_TOKEN_KEY); localStorage.removeItem(USER_KEY); }
export function getAccessToken() { return localStorage.getItem(ACCESS_TOKEN_KEY); }
export function getCurrentUser() { try { return JSON.parse(localStorage.getItem(USER_KEY)); } catch { return null; } }
export function login(email, password) { return postJson('/api/auth/login', { email, password }); }
export function register(email, password, code) { return postJson('/api/auth/register', { email, password, code }); }
export function resetPassword(email, password, code) { return postJson('/api/auth/reset-password', { email, password, code }); }
export function sendCode(email, purpose) { return postJson('/api/auth/send-code', { email, purpose }); }
export function logout() { const refreshToken = localStorage.getItem(REFRESH_TOKEN_KEY); return refreshToken ? postJson('/api/auth/logout', { refreshToken }) : Promise.resolve(); }
