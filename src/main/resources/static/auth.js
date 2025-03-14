document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.getElementById("loginForm");
    const registerForm = document.getElementById("registerForm");
    const showLogin = document.getElementById("showLogin");
    const showRegister = document.getElementById("showRegister");

    // Cambiar entre pestañas
    showLogin.addEventListener("click", () => {
        loginForm.classList.remove("hidden");
        registerForm.classList.add("hidden");
        showLogin.classList.add("active");
        showRegister.classList.remove("active");
    });

    showRegister.addEventListener("click", () => {
        registerForm.classList.remove("hidden");
        loginForm.classList.add("hidden");
        showRegister.classList.add("active");
        showLogin.classList.remove("active");
    });

    // Manejo del registro
    registerForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const email = document.getElementById("regEmail").value;
        const password = document.getElementById("regPassword").value;

        try {
            const response = await fetch("https://gestionpropiedadesbackend.duckdns.org:8080/api/auth/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email, password })
            });

            if (response.ok) {
                alert("Registro exitoso, ahora inicia sesión.");
                showLogin.click();
            } else {
                const errorText = await response.text();
                alert("Error en el registro: " + errorText);
            }
        } catch (error) {
            alert("Error en la conexión: " + error.message);
        }
    });

    // Manejo del login
    loginForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const email = document.getElementById("loginEmail").value;
        const password = document.getElementById("loginPassword").value;

        try {
            const response = await fetch("https://gestionpropiedadesbackend.duckdns.org:8080/api/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email, password })
            });

            if (response.ok) {
                const data = await response.text();
                alert("Inicio de sesión exitoso: " + data);
                window.location.href = "index.html";
            } else {
                const errorText = await response.text();
                alert("Error en el login: " + errorText);
            }
        } catch (error) {
            alert("Error en la conexión: " + error.message);
        }
    });
});
