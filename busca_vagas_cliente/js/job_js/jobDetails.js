import { JobService } from "../job_js/jobService.js";

class JobDetails {
    constructor() {
        this.containerActions = document.getElementById("containerActions");
        this.jobContent = document.getElementById("jobContent");
        this.containerCandidates = document.getElementById("candidates");

        const params = new URLSearchParams(window.location.search);
        const id = params.get("id");
        this.id = id;
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
            const btnEdit = this.containerActions.querySelector('.btn-edit');
            const btnDelete = this.containerActions.querySelector('.btn-delete');
            const btnCandidates = this.containerActions.querySelector('.btn-candidates');


            if (btnEdit) {
                btnEdit.addEventListener('click', () => {
                    window.location.href = `../jobs_html/update_job.html?id=${job.job_id}`;
                })
            }
            if (btnDelete) {
                btnDelete.addEventListener('click', async () => {
                    if (window.confirm("Deseja mesmo excluir?")) {
                        const jobService = new JobService();
                        try {
                            const result = await jobService.deleteJob(this.id, localStorage.getItem("token"));

                            setTimeout(() => {
                                window.location.replace("../jobs_html/company_jobs.html");
                            }, 1000)

                        } catch (e) {
                            console.log(e);
                            if (e.message) {
                                console.log("Detalhes:", e.response);
                            }
                        }
                    }
                })
            }
            if (btnCandidates) {
                btnCandidates.addEventListener('click', async () => {
                    const jobService = new JobService();
                    try {
                        const result = await jobService.getAllJobsCandidates(localStorage.getItem("token"), localStorage.getItem("id"), this.id);

                        if (result.items.length === 0) {
                            const htmlCard = `
                                <h3>Nenhum aplicante</h3>
                            `
                            this.containerCandidates.innerHTML = htmlCard;

                        } else {

                            result.items.forEach(c => {
                                const htmlCard = `
                                <div class="card candidate-card">
                                            <h3>${c.name}</h3>
                                            <p><strong>ID:</strong>${c.user_id}</p>
                                            <p><strong>E-mail:</strong> ${c.email}</p>
                                            <p><strong>Phone:</strong> ${c.phone}</p>
                                            <p><strong>Education:</strong> ${c.education}</p>
                                            <p><strong>Experience:</strong> ${c.experience}</p>         

                                            <input type="text" placeholder="Envie seu Feedback" class="input-feedback">
                                            <button class="btn-feedback" data-id="${c.user_id}" >
                                            Enviar Feedback
                                            </button>
                                        </div>
                            `;

                                this.containerCandidates.insertAdjacentHTML('beforeend', htmlCard);
                            });
                            const feedbackButtons = this.containerCandidates.querySelectorAll(".btn-feedback");
                            feedbackButtons.forEach(btn => {
                                btn.addEventListener('click', (event) => {
                                    const idUser = btn.getAttribute('data-id');
                                    const card = btn.closest('.candidate-card');
                                    const feedbackInput = card.querySelector(".input-feedback")
                                    this.feedbackClick(idUser, feedbackInput.value, this.id);
                                })
                            })
                        }
                    } catch (e) {
                        console.log(e);
                    }
                });
            }
        } else if (role === "user") {
            this.containerActions.innerHTML = `
                <button class="btn-apply" >Candidatar-se</button>
            `
            const btnApply = this.containerActions.querySelector('.btn-apply');

            if (btnApply) {
                btnApply.addEventListener('click', () => {
                    window.location.href = `../jobs_html/apply_job.html?id=${job.job_id}`;
                })
            }
        }
    }
    async feedbackClick(idUser, feedback, idJob){
        console.log (idJob + feedback);
        const user_id = parseInt(idUser)
        const message = {
            user_id : user_id,
            message : feedback
        }
        const jobService = new JobService();
        const result = await jobService.sendFeedback(localStorage.getItem("token"), message, idJob);
    }
}

new JobDetails();