/**
 * Jeenify — Auth Module (auth.js)
 *
 * Handles:
 *   - Login (POST /api/auth/login)
 *   - studentId resolution (GET /api/students/me/{userId})
 *   - Persisting session to localStorage
 *   - Role-based redirect: /admin → /teacher → /student
 */

const API_BASE = 'http://localhost:8080';

/* ─────────────────────────────────────────
   Storage helpers
───────────────────────────────────────── */

/**
 * Saves all auth data returned from the login endpoint.
 * @param {{ token: string, role: string, name: string, userId: number }} data
 */
function saveSession(data) {
  localStorage.setItem('jfy_token',  data.token);
  localStorage.setItem('jfy_role',   data.role);
  localStorage.setItem('jfy_name',   data.name);
  localStorage.setItem('jfy_userId', String(data.userId));
}

/**
 * Saves the resolved studentId (only relevant for STUDENT role).
 * @param {number|string} studentId
 */
function saveStudentId(studentId) {
  localStorage.setItem('jfy_studentId', String(studentId));
}

/**
 * Clears all Jeenify session data from localStorage.
 */
function clearSession() {
  ['jfy_token', 'jfy_role', 'jfy_name', 'jfy_userId', 'jfy_studentId']
    .forEach(key => localStorage.removeItem(key));
}

/* ─────────────────────────────────────────
   API helpers
───────────────────────────────────────── */

/**
 * Generalized, typed fetch wrapper.
 * @param {string} path  — endpoint path (no base URL)
 * @param {RequestInit} [options]
 * @param {string} [token] — Bearer token if required
 * @returns {Promise<any>}
 */
async function apiFetch(path, options = {}, token = null) {
  const headers = { 'Content-Type': 'application/json', ...options.headers };
  if (token) headers['Authorization'] = `Bearer ${token}`;

  const res = await fetch(`${API_BASE}${path}`, { ...options, headers });

  if (!res.ok) {
    // Try to surface the server error message
    let message = `Request failed (${res.status})`;
    try {
      const body = await res.text();
      if (body) message = body;
    } catch (_) { /* ignore */ }
    throw new Error(message);
  }

  // 204 No Content or empty body
  const text = await res.text();
  return text ? JSON.parse(text) : null;
}

/**
 * Fetches the full student profile to resolve studentId.
 * This MUST be called right after login when role === 'STUDENT'.
 * @param {number} userId
 * @param {string} token
 * @returns {Promise<{ studentId: number, status: string, [key: string]: any }>}
 */
async function fetchStudentProfile(userId, token) {
  return apiFetch(`/api/students/me/${userId}`, { method: 'GET' }, token);
}

/* ─────────────────────────────────────────
   Redirect logic
───────────────────────────────────────── */

/**
 * Redirects to the correct dashboard after a successful login.
 * @param {'ADMIN' | 'TEACHER' | 'STUDENT'} role
 */
function redirectByRole(role) {
  const routes = {
    ADMIN:   'admin.html',
    TEACHER: 'teacher.html',
    STUDENT: 'student.html',
  };
  const target = routes[role] ?? 'index.html';
  window.location.href = target;
}

/* ─────────────────────────────────────────
   UI helpers
───────────────────────────────────────── */

/** Shows the red error banner. */
function showError(message) {
  const err = document.getElementById('login-error');
  const msg = document.getElementById('login-error-msg');
  const loading = document.getElementById('login-loading');
  if (!err) return;
  msg.textContent = message;
  loading.hidden = true;
  err.hidden = false;
}

/** Shows the green loading banner. */
function showLoading(message = 'Signing you in…') {
  const loading = document.getElementById('login-loading');
  const err = document.getElementById('login-error');
  const loadMsg = document.getElementById('login-loading-msg');
  if (!loading) return;
  loadMsg.textContent = message;
  err.hidden = true;
  loading.hidden = false;
}

/** Hides both banners. */
function clearFeedback() {
  const err     = document.getElementById('login-error');
  const loading = document.getElementById('login-loading');
  if (err)     err.hidden = true;
  if (loading) loading.hidden = true;
}

/** Marks an input as invalid with a helper message. */
function setFieldError(inputId, errorId, message) {
  const input = document.getElementById(inputId);
  const span  = document.getElementById(errorId);
  if (input) input.classList.add('invalid');
  if (span)  span.textContent = message;
}

/** Clears field-level errors. */
function clearFieldErrors() {
  document.querySelectorAll('.input-wrapper input').forEach(el => el.classList.remove('invalid'));
  document.querySelectorAll('.field-error').forEach(el => el.textContent = '');
}

/** Enables / disables the submit button. */
function setLoading(isLoading) {
  const btn = document.getElementById('login-btn');
  if (!btn) return;
  btn.disabled = isLoading;
  btn.querySelector('.btn-text').textContent = isLoading ? 'Signing in…' : 'Sign in';
}

/* ─────────────────────────────────────────
   Core Login Logic
───────────────────────────────────────── */

/**
 * Main login handler:
 *   1. Validates fields
 *   2. POST /api/auth/login
 *   3. Persists token, role, name, userId
 *   4. If STUDENT → GET /api/students/me/{userId} → persist studentId
 *   5. Redirects based on role
 *
 * @param {string} email
 * @param {string} password
 */
async function handleLogin(email, password) {
  clearFieldErrors();
  clearFeedback();

  // ── Client-side validation
  let hasError = false;

  if (!email) {
    setFieldError('login-email', 'email-error', 'Email is required.');
    hasError = true;
  } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
    setFieldError('login-email', 'email-error', 'Enter a valid email address.');
    hasError = true;
  }

  if (!password) {
    setFieldError('login-password', 'password-error', 'Password is required.');
    hasError = true;
  } else if (password.length < 6) {
    setFieldError('login-password', 'password-error', 'Password must be at least 6 characters.');
    hasError = true;
  }

  if (hasError) return;

  setLoading(true);
  showLoading('Signing you in…');

  try {
    // ── Step 1: Authenticate
    const loginData = await apiFetch('/api/auth/login', {
      method: 'POST',
      body: JSON.stringify({ email, password }),
    });

    // loginData = { token, role, name, userId }
    saveSession(loginData);

    const { token, role, userId } = loginData;

    // ── Step 2: If STUDENT, resolve studentId immediately
    if (role === 'STUDENT') {
      showLoading('Loading your profile…');

      try {
        const profile = await fetchStudentProfile(userId, token);
        // profile = { studentId, userId, studentNumber, majorCourseId, status }
        saveStudentId(profile.studentId);
      } catch (profileErr) {
        // Non-fatal: student can still proceed; log for debugging
        console.warn('[Jeenify] Could not resolve studentId:', profileErr.message);
      }
    }

    // ── Step 3: Redirect
    showLoading('Redirecting…');
    redirectByRole(role);

  } catch (err) {
    setLoading(false);
    // Surface a friendly message for common 401 / 403 errors
    if (err.message.includes('401') || err.message.toLowerCase().includes('unauthorized')) {
      showError('Incorrect email or password. Please try again.');
    } else if (err.message.includes('403')) {
      showError('Your account has not been approved yet. Please wait for admin approval.');
    } else if (err.message.includes('Failed to fetch') || err.message.includes('NetworkError')) {
      showError('Cannot reach the server. Please check your connection and try again.');
    } else {
      showError(err.message || 'An unexpected error occurred. Please try again.');
    }
  }
}

/* ─────────────────────────────────────────
   Session guard — redirect if already logged in
───────────────────────────────────────── */
(function guardAlreadyLoggedIn() {
  const token = localStorage.getItem('jfy_token');
  const role  = localStorage.getItem('jfy_role');
  if (token && role) {
    redirectByRole(role);
  }
})();

/* ─────────────────────────────────────────
   DOM Wiring (runs when page is ready)
───────────────────────────────────────── */
document.addEventListener('DOMContentLoaded', () => {

  // ── Password toggle
  const toggleBtn   = document.getElementById('toggle-password');
  const passwordInput = document.getElementById('login-password');
  const eyeOpen     = document.getElementById('eye-open');
  const eyeClosed   = document.getElementById('eye-closed');

  if (toggleBtn && passwordInput) {
    toggleBtn.addEventListener('click', () => {
      const isHidden = passwordInput.type === 'password';
      passwordInput.type = isHidden ? 'text' : 'password';
      // When password is visible → show closed-eye (click to hide)
      // When password is hidden  → show open-eye (click to show)
      eyeOpen.style.display   = isHidden ? 'none'  : 'block';
      eyeClosed.style.display = isHidden ? 'block' : 'none';
      toggleBtn.setAttribute('aria-label', isHidden ? 'Hide password' : 'Show password');
    });
  }

  // ── Input: clear error styling on change
  document.querySelectorAll('.input-wrapper input').forEach(input => {
    input.addEventListener('input', () => {
      input.classList.remove('invalid');
      const errorSpan = document.getElementById(`${input.name}-error`);
      if (errorSpan) errorSpan.textContent = '';
      clearFeedback();
    });
  });

  // ── Form submission
  const form = document.getElementById('login-form');
  if (form) {
    form.addEventListener('submit', (e) => {
      e.preventDefault();
      const email    = document.getElementById('login-email')?.value.trim() ?? '';
      const password = document.getElementById('login-password')?.value ?? '';
      handleLogin(email, password);
    });
  }

});
