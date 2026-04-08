/**
 * Jeenify — API Helper (api.js)
 * Shared fetch wrapper that automatically attaches the Bearer token.
 */

const API_BASE = 'http://localhost:8080';

/* ─────────────────────────────────────
   Session helpers
───────────────────────────────────── */
function getToken()     { return localStorage.getItem('jfy_token'); }
function getRole()      { return localStorage.getItem('jfy_role'); }
function getUserId()    { return localStorage.getItem('jfy_userId'); }
function getStudentId() { return localStorage.getItem('jfy_studentId'); }
function getUserName()  { return localStorage.getItem('jfy_name'); }

function clearSession() {
  ['jfy_token', 'jfy_role', 'jfy_name', 'jfy_userId', 'jfy_studentId']
    .forEach(k => localStorage.removeItem(k));
}

function logout() {
  clearSession();
  window.location.href = 'index.html';
}

/**
 * Guard — redirect to login if no token, or to own dashboard if wrong role.
 */
function requireAuth(allowedRoles) {
  const token = getToken();
  const role  = getRole();
  if (!token || !role) { window.location.href = 'index.html'; return false; }
  if (!allowedRoles.includes(role)) {
    const routes = { ADMIN: 'admin.html', TEACHER: 'teacher.html', STUDENT: 'student.html' };
    window.location.href = routes[role] || 'index.html';
    return false;
  }
  return true;
}

/* ─────────────────────────────────────
   Fetch wrapper with auto Bearer token
───────────────────────────────────── */
async function api(path, options = {}) {
  const token = getToken();
  const headers = {
    'Content-Type': 'application/json',
    ...(options.headers || {}),
  };
  if (token) headers['Authorization'] = `Bearer ${token}`;

  const res = await fetch(`${API_BASE}${path}`, { ...options, headers });

  if (res.status === 401 || res.status === 403) {
    const text = await res.text().catch(() => '');
    throw new Error(text || (res.status === 401 ? 'Unauthorized' : 'Forbidden'));
  }

  if (!res.ok) {
    const text = await res.text().catch(() => '');
    throw new Error(text || `Request failed (${res.status})`);
  }

  const text = await res.text();
  if (!text) return null;
  try { return JSON.parse(text); } catch { return text; }
}

/* Convenience methods */
const API = {
  get:    (path) => api(path, { method: 'GET' }),
  post:   (path, body) => api(path, { method: 'POST', body: JSON.stringify(body) }),
  put:    (path, body) => api(path, { method: 'PUT', ...(body ? { body: JSON.stringify(body) } : {}) }),
  delete: (path) => api(path, { method: 'DELETE' }),
};