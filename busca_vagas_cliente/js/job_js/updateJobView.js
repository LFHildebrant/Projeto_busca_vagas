
import { JobService } from "../job_js/jobService.js";

class UpdateJobView {
    constructor() {
        this.message = document.getElementById("message");
        this.profile = document.getElementById("profile");
        this.inputTitle = document.getElementById("title");
        this.inputArea = document.getElementById("area");
        this.inputDescription = document.getElementById("description");
        this.inputState = document.getElementById("state");
        this.inputCity = document.getElementById("city");
        this.inputSalary = document.getElementById("salary");
        this.createForm = document.getElementById("userForm");

        const params = new URLSearchParams(window.location.search);
        const id = params.get("id");
        this.id = id;
        this.getDataJOb(this.id);

        this.createForm.addEventListener("submit", this.handleSubmitCreate.bind(this));
    }

    async getDataJOb(id) {
        const jobService = new JobService();
        const job =  await jobService.getDataJob(localStorage.getItem("token"), id);

        this.inputTitle.value = job.title;
        this.inputArea.value = job.area;
        this.inputDescription.value = job.description;
        this.inputState.value =job.state;
        this.inputCity.value = job.city;
        this.inputSalary.value = job.salary;

    }
    async handleSubmitCreate(event) {
        event.preventDefault();
        const jobService = new JobService();
        const fields = ["title", "area", "description", "state", "city", "salary"];

        const job = {};

        fields.forEach(field => {
            const value = document.getElementById(field).value.trim();
            /*if (value !== "") {
                user[field] = value;
            }*/
            job[field] = value;
        });

        try {
            console.log(job);
            const result = await jobService.updateJob(this.id, localStorage.getItem("token"), job);

            this.message.style.color = "green";
            this.message.textContent = "Sucesso! -> " + result.message + " REDIRECIONANDO....";
            this.message.classList.remove("hide");

            setTimeout(() => {
                window.location.replace("../company_home.html");
            }, 2000)
        } catch (e) {
            console.log(e);
            if (e.message) {
                console.log("Detalhes:", e.response);
            }
        }
    }
}

new UpdateJobView();