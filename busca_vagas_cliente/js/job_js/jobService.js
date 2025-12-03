
export class JobService {

    constructor() { }

    async createJob(dataJob, token) {
        console.log("📤 ENVIANDO requisição POST para:" + sessionStorage.getItem("BASE_URL") + "/jobs");
        console.log("📦 Corpo da requisição:", JSON.stringify(dataJob, null, 2));
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/jobs", {
            method: "POST",
            body: JSON.stringify(dataJob),
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${token}`
            },
        });
        if (response.ok) {
            const data = await response.json();
            console.log("RECEIVED CREATE JOB JSON: " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);
            throw new Error("Erro no create: " + JSON.stringify(errorData, null, 2));
        }
    }
    async getAllJobsbyCompany(token, id, filters) {
        console.log("📤 ENVIANDO requisição POST para:" + sessionStorage.getItem("BASE_URL") + "/companies" + "/" + id + "/jobs");
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/companies" + "/" + id + "/jobs", {
            method: "POST",
            body: JSON.stringify(filters),
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${token}`
            },
        });
        if (response.ok) {
            const data = await response.json();
            console.log("RECEIVED JOBS : " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);
            throw new Error("Erro ao post jobs por empresa: " + JSON.stringify(errorData, null, 2));
        }
    }
    async getAllJobs(token, filters) {
        console.log("📤 ENVIANDO requisição POST para:" + sessionStorage.getItem("BASE_URL") + "/jobs" + "/search");
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/jobs" + "/search", {
            method: "POST",
            body: JSON.stringify(filters),
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${token}`
            },
        });
        if (response.ok) {
            const data = await response.json();
            console.log("RECEIVED JOBS : " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);
            throw new Error("Erro ao post jobs todos: " + JSON.stringify(errorData, null, 2));
        }
    }
    async getDataJob(token, id) {
        console.log("📤 ENVIANDO requisição GET para:" + sessionStorage.getItem("BASE_URL") + "/jobs" + "/" + id);
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/jobs" + "/" + id, {
            method: "GET",
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${token}`
            },
        });
        if (response.ok) {
            const data = await response.json();
            console.log("RECEIVED GET JOB   : " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);
            throw new Error("Erro ao getDados: " + JSON.stringify(errorData, null, 2));
        }
    }
    async deleteJob(id, token) {
        console.log("📤 ENVIANDO requisição DELETE para:" + sessionStorage.getItem("BASE_URL") + "/jobs" + "/" + id);
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/jobs" + "/" + id, {
            method: "DELETE",
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${token}`
            },
        });
        if (response.ok) {
            const data = await response.json();
            console.log("RECEIVED DELETE JOB: " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);
            throw new Error("Erro ao deletar: " + JSON.stringify(errorData, null, 2));
        }
    }
    async updateJob(id, token, dataJob) {

        console.log("📤 ENVIANDO requisição PATCH para:" + sessionStorage.getItem("BASE_URL") + "/jobs" + "/" + id);
        console.log("📦 Corpo da requisição:", JSON.stringify(dataJob, null, 2));
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });

        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/jobs" + "/" + id, {
            method: "PATCH",
            body: JSON.stringify(dataJob),
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${token}`
            },
        });
        if (response.ok) {
            const data = await response.json();
            console.log("RECEIVED UPDATE JOB: " + JSON.stringify(data, null, 2))
            return data;
        } else {
            const errorData = await response.json().catch(() => null);
            throw new Error("Erro no update: " + JSON.stringify(errorData, null, 2));
        }
    }
    async getAllJobsCandidates(token, companyId, jobId) {
        console.log("📤 ENVIANDO requisição GET para:" + sessionStorage.getItem("BASE_URL") + "/companies" + "/" + companyId + "/jobs" + "/" + jobId);
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/companies" + "/" + companyId + "/jobs" + "/" + jobId, {
            method: "GET",
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${token}`
            },
        });
        if (response.ok) {
            const data = await response.json();
            console.log("RECEIVED CANDIDATES : " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);
            throw new Error("Erro ao get candidates : " + JSON.stringify(errorData, null, 2));
        }
    }
    async applyJob(token, idJob, dataUser) {
        console.log("📤 ENVIANDO requisição POST para:" + sessionStorage.getItem("BASE_URL") + "/jobs" + "/" + idJob);
        console.log("📦 Corpo da requisição:", JSON.stringify(dataUser, null, 2));
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/jobs" + "/" + idJob, {
            method: "POST",
            body: JSON.stringify(dataUser),
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${token}`
            },
        });
        if (response.ok) {
            const data = await response.json();
            console.log("RECEIVED APPLIED JOB JSON: " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);
            throw new Error("Erro no apply: " + JSON.stringify(errorData, null, 2));
        }
    }
    async getAllJobsAppUser(token, id) {
        console.log("📤 ENVIANDO requisição GET para:" + sessionStorage.getItem("BASE_URL") + "/users" + "/" + id + "/jobs");
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/users" + "/" + id + "/jobs" , {
            method: "GET",
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${token}`
            },
        });
        if (response.ok) {
            const data = await response.json();
            console.log("RECEIVED JOBS : " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);
            throw new Error("Erro ao get job appl : " + JSON.stringify(errorData, null, 2));
        }
    }
    async sendFeedback(token, feedback, idJob) {
        console.log("📤 ENVIANDO requisição POST para:" + sessionStorage.getItem("BASE_URL") + "/jobs" + "/" + idJob + "/feedback");
        console.log("📦 Corpo da requisição:", JSON.stringify(feedback, null, 2));
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/jobs" + "/" + idJob + "/feedback", {
            method: "POST",
            body: JSON.stringify(feedback),
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${token}`
            },
        });
        if (response.ok) {
            const data = await response.json();
            console.log("RECEIVED FEEDBACK JSON: " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);
            throw new Error("Erro no feedback: " + JSON.stringify(errorData, null, 2));
        }
    }
}