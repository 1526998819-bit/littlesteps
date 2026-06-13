/**
 * LittleSteps (成长点滴) API Client
 * Base URL: /api
 * Auto-redirects to login if not authenticated
 */

const API_BASE = '/api';

// --- Generic fetch wrapper ---
async function apiFetch(url, options = {}) {
    try {
        const response = await fetch(url, {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers,
            },
            ...options,
        });

        if (response.status === 401 || response.status === 403) {
            window.location.href = '/login.html';
            throw new Error('请先登录');
        }

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }

        const result = await response.json();
        return result;
    } catch (error) {
        console.error('API Error:', error);
        throw error;
    }
}

// --- Auth check on page load ---
async function checkAuth() {
    try {
        const res = await fetch(`${API_BASE}/auth/me`);
        const data = await res.json();
        if (!data.success) {
            window.location.href = '/login.html';
            return null;
        }
        return data.data;
    } catch (e) {
        window.location.href = '/login.html';
        return null;
    }
}

// --- Auth API ---
const AuthAPI = {
    login(username, password) {
        return apiFetch(`${API_BASE}/auth/login`, {
            method: 'POST',
            body: JSON.stringify({ username, password }),
        });
    },
    register(username, password) {
        return apiFetch(`${API_BASE}/auth/register`, {
            method: 'POST',
            body: JSON.stringify({ username, password }),
        });
    },
    logout() {
        return apiFetch(`${API_BASE}/auth/logout`, { method: 'POST' });
    },
    me() {
        return apiFetch(`${API_BASE}/auth/me`);
    },
};

// --- Dashboard API ---
const DashboardAPI = {
    get(childId = 1) {
        return apiFetch(`${API_BASE}/dashboard?childId=${childId}`);
    },
};

// --- Child API ---
const ChildAPI = {
    list() {
        return apiFetch(`${API_BASE}/children`);
    },

    get(id) {
        return apiFetch(`${API_BASE}/children/${id}`);
    },

    create(data) {
        return apiFetch(`${API_BASE}/children`, {
            method: 'POST',
            body: JSON.stringify(data),
        });
    },

    update(id, data) {
        return apiFetch(`${API_BASE}/children/${id}`, {
            method: 'PUT',
            body: JSON.stringify(data),
        });
    },

    delete(id) {
        return apiFetch(`${API_BASE}/children/${id}`, {
            method: 'DELETE',
        });
    },
};

// --- Growth API ---
const GrowthAPI = {
    list(childId) {
        return apiFetch(`${API_BASE}/growth?childId=${childId}`);
    },

    create(data) {
        return apiFetch(`${API_BASE}/growth`, {
            method: 'POST',
            body: JSON.stringify(data),
        });
    },

    delete(id) {
        return apiFetch(`${API_BASE}/growth/${id}`, {
            method: 'DELETE',
        });
    },
};

// --- Journal API ---
const JournalAPI = {
    list(childId, category) {
        let url = `${API_BASE}/journal?childId=${childId}`;
        if (category) url += `&category=${category}`;
        return apiFetch(url);
    },

    create(data) {
        return apiFetch(`${API_BASE}/journal`, {
            method: 'POST',
            body: JSON.stringify(data),
        });
    },

    delete(id) {
        return apiFetch(`${API_BASE}/journal/${id}`, {
            method: 'DELETE',
        });
    },
};

// --- Vaccine API ---
const VaccineAPI = {
    list(childId, status) {
        let url = `${API_BASE}/vaccines?childId=${childId}`;
        if (status) url += `&status=${status}`;
        return apiFetch(url);
    },

    pending(childId) {
        return apiFetch(`${API_BASE}/vaccines/pending?childId=${childId}`);
    },

    create(data) {
        return apiFetch(`${API_BASE}/vaccines`, {
            method: 'POST',
            body: JSON.stringify(data),
        });
    },

    update(id, data) {
        return apiFetch(`${API_BASE}/vaccines/${id}`, {
            method: 'PUT',
            body: JSON.stringify(data),
        });
    },

    delete(id) {
        return apiFetch(`${API_BASE}/vaccines/${id}`, {
            method: 'DELETE',
        });
    },
};

// --- Gallery API ---
const GalleryAPI = {
    list(childId) {
        return apiFetch(`${API_BASE}/gallery?childId=${childId}`);
    },

    recent(childId) {
        return apiFetch(`${API_BASE}/gallery/recent?childId=${childId}`);
    },

    create(data) {
        return apiFetch(`${API_BASE}/gallery`, {
            method: 'POST',
            body: JSON.stringify(data),
        });
    },

    delete(id) {
        return apiFetch(`${API_BASE}/gallery/${id}`, {
            method: 'DELETE',
        });
    },
};

// Export for use in other scripts
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        AuthAPI, DashboardAPI, ChildAPI, GrowthAPI,
        JournalAPI, VaccineAPI, GalleryAPI,
    };
}
