import { JobService } from "../job_js/jobService.js";

class JobDetails {
    constructor() {
        this.containerActions = document.getElementById("containerActions");
        this.jobContent = document.getElementById("jobContent");

        const params = new URLSearchParams(window.location.search);
        const id = params.get("id");
        this.getJob(id);
    }

    async getJob(idJob) {

        const jobService = new JobService();
        const job = await jobService.getDataJob(localStorage.getItem("token"), idJob);

        const salarioFormatado = job.salary
            ? parseFloat(job.salary).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
            : 'A combinar';



        const htmlCard = `
                <div class="card job-card">
                            <h3>${job.title}</h3>
                            <p><strong>ID:</strong>${job.job_id}</p>
                            <p><span class="badge">${job.area}</span></p>
                            <p><strong>Empresa:</strong> ${job.company}</p>
                            <p><strong>Descrição:</strong> ${job.description}</p>
                            <p><strong>Contato:</strong> ${job.contact}</p>
                            <p><strong>Local:</strong> ${job.city} - ${job.state}</p>
                            <p><strong>Salário:</strong> ${salarioFormatado}</p>
                            
                        </div>
            `;

        this.jobContent.insertAdjacentHTML('beforeend', htmlCard);
        this.createActions(job);
    }

    createActions(job) {
        this.containerActions.innerHTML = "";
        const role = localStorage.getItem("role");

        if (role === "company") {
            this.containerActions.innerHTML = `
                <button class="btn-edit">Editar Vaga</button>
                <button class="btn-delete">Excluir Vaga</button>
                <button class="btn-candidates">Ver Candidatos</button>
            `

        } else if (role === "user") {
            this.containerActions.innerHTML = `
                <button class="btn-apply" onclick="applyToJob(${job.id})">
                    Candidatar-se
                </button>
            `
        }
    }
}

new JobDetails();