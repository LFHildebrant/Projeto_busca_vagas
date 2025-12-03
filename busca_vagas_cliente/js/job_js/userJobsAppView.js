import { JobService } from "../job_js/jobService.js";

class UserJobsAppView {
    constructor() {
        this.containerJobs = document.getElementById("container-jobs-app");
        this.getjobs();
    }

    async getjobs() {
        const jobService = new JobService();
        const result = await jobService.getAllJobsAppUser(localStorage.getItem("token"), localStorage.getItem("id"));

        result.items.forEach(job => {
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
                            <p><strong>Contato:</strong> ${job.contact}</p>
                            <p><strong>Feedback:</strong> ${job.feedback || "Aguardando..." }</p>
                        </div>
            `;

        this.containerJobs.insertAdjacentHTML('beforeend', htmlCard);
        });
    }
}

new UserJobsAppView();