import api from "./api";

export const login = async (email, password) => {
    const response = await api.post("/auth/login", {
        email,
        password,
    });

    localStorage.setItem("token", response.data.token);
    localStorage.setItem("refreshToken", response.data.refreshToken);

    return response.data;
};

export const logout = async () => {
    await api.post("/auth/logout");
    localStorage.clear();
};
