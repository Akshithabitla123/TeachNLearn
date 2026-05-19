<script>

function goToLogin() {

    window.location.href = "./pages/login.html";
}

async function loadSkills() {

    const response = await fetch("http://localhost:9090/skills");

    const skills = await response.json();

    const container = document.getElementById("skillsContainer");

    container.innerHTML = "";

    skills.forEach(skill => {

        container.innerHTML += `
        <div class="skill-card">
            <h3>${skill.title}</h3>
            <p>${skill.description}</p>
            <span>${skill.experienceLevel}</span>
        </div>
        `;
    });
}

loadSkills();

</script>