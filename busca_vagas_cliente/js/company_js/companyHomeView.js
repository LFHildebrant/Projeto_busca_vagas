import { LogoutView } from "../logoutView.js";
import { JobService } from "../job_js/jobService.js";


class CompanyHomeView {
    constructor() {
        this.form = document.getElementById("filtersForm");
        this.btnAdd = document.getElementById("moreFilters");

        this.filtersContainer = document.getElementById("filtersContainer");

        this.jobsContainer = document.getElementById("jobsContainer");

        this.statusMsg = document.getElementById("statusMessage");

        this.btnAdd.addEventListener("click", () => this.addFilterHTML());
        this.form.addEventListener("submit", (e) => this.handleSubmit(e));

        const requestBody = {
            filters: []
        }
        this.getJobs(requestBody);
    }
    addFilterHTML() {
        const count = document.querySelectorAll('.filter-group').length + 1;

        const template = `
                <div class="filter-group"> <h3>Filtro #${count}</h3>
                    
                    <label>Title</label>
                    <input type="text" class="input-title" name="title">
    
                    <label>Area</label>
                    <input type="text" class="input-area" name="area">
    
                    <label>State</label>
                    <input type="text" class="input-state" name="state">
    
                    <label>City</label>
                    <input type="text" class="input-city" name="city">
    
                    <label>Salary Min</label>
                    <input type="number" class="input-salary-min" name="salaryMin">
    
                    <label>Salary Max</label>
                    <input type="number" class="input-salary-max" name="salaryMax">
                </div>
            `;

        this.filtersContainer.insertAdjacentHTML('beforeend', template);
    }

    async handleSubmit(event) {
        event.preventDefault();

        const groups = document.querySelectorAll('.filter-group');
        const filtersArray = [];

        groups.forEach(group => {

            const title = group.querySelector('.input-title').value;
            const area = group.querySelector('.input-area').value;
            const state = group.querySelector('.input-state').value;
            const city = group.querySelector('.input-city').value;
            const min = group.querySelector('.input-salary-min').value;
            const max = group.querySelector('.input-salary-max').value;

            const filterDto = {
                title: title || null,
                area: area || null,
                state: state || null,
                city: city || null,
                salary_range: (min || max) ? {
                    min: min ? parseFloat(min) : null,
                    max: max ? parseFloat(max) : null
                } : null
            };

            filtersArray.push(filterDto);
        });

        const requestBody = {
            filters: filtersArray
        };

        console.log("Enviando para o Back:", requestBody);
        this.getJobs(requestBody);
    }

    async getJobs(filters) {
        const jobService = new JobService();
        this.statusMsg.textContent = "Carregando vagas...";

        this.jobsContainer.innerHTML = "";

        try {
            const response = await jobService.getAllJobs(
                localStorage.getItem("token"),
                filters
            );
            console.log(response)

            const jobsArray = response.items || response.jobs || [];

            this.renderJobs(jobsArray);

        } catch (error) {
            console.error(error);
            this.statusMsg.textContent = "Erro ao buscar vagas: " + error.message;
            this.statusMsg.style.color = "red";
        }
    }

    renderJobs(jobs) {
        this.statusMsg.textContent = "";

        if (!jobs || jobs.length === 0) {
            this.jobsContainer.innerHTML = "<p>Nenhuma vaga encontrada para estes filtros.</p>";
            return;
        }

        jobs.forEach(job => {
            const salarioFormatado = job.salary
                ? parseFloat(job.salary).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
                : 'A combinar';

            // Verifique se o ID vem como 'id' ou 'job_id' (seu log anterior mostrava job_id)
            const jobId = job.id || job.job_id;

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
                            
                            <a href="./jobs_html/job_details.html?id=${jobId}" class="btn-details">
                                <button>Ver Detalhes</button>
                            </a>
                        </div>
                `;

            this.jobsContainer.insertAdjacentHTML('beforeend', htmlCard);
        });
    }

}


new CompanyHomeView();
new LogoutView();