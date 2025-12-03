import { JobService } from "../job_js/jobService.js";

class ApplyJobView {
    constructor() {
        this.inputName = document.getElementById("name");
        this.inputEmail = document.getElementById("email");
        this.inputPhone = document.getElementById("phone");
        this.inputExperience = document.getElementById("experience");
        this.inputEducation = document.getElementById("education");
        this.userForm = document.getElementById("userForm");

        const params = new URLSearchParams(window.location.search);
        const id = params.get("id");
        this.id = id;

        this.userForm.addEventListener("submit", this.handleSubmitCreate.bind(this));
    }
    async handleSubmitCreate(e) {
        e.preventDefault();
        const fields = ["name", "phone", "email", "experience", "education"];

        const user = {};

        fields.forEach(field => {
            const value = document.getElementById(field).value.trim();
            user[field] = value;
        });

        try {
            const jobService = new JobService();
            const result = await jobService.applyJob(localStorage.getItem("token"), this.id , user);

        } catch (e) {
            console.log(e);
        }
    }
}
new ApplyJobView();