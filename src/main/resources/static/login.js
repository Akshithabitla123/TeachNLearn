async function loginUser() {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch("http://localhost:9090/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    });

    const data = await response.json();

    if(response.ok) {
        alert("Login successful");

        localStorage.setItem("user", JSON.stringify(data));

        window.location.href = "index.html";
    } else {
        alert(data.message || "Login failed");
    }
}