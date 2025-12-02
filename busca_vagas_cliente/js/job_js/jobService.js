
export class JobService{

    constructor(){}

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
            if (response.ok){
                const data = await response.json();
                console.log("RECEIVED CREATE JOB JSON: " + JSON.stringify(data, null, 2));
                return data;
            } else {
                const errorData = await response.json().catch(() => null);                    
                throw new Error("Erro no create: " + JSON.stringify(errorData, null, 2));
            }               
        }
    async getAllJobsbyCompany(token, id, filters){
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
        if (response.ok){
            const data = await response.json();
            console.log("RECEIVED JOBS : " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);                    
            throw new Error("Erro ao post jobs por empresa: " + JSON.stringify(errorData, null, 2));
        }       
    }
    async getAllJobs(token, filters){
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
        if (response.ok){
            const data = await response.json();
            console.log("RECEIVED JOBS : " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);                    
            throw new Error("Erro ao post jobs todos: " + JSON.stringify(errorData, null, 2));
        }       
    }
    async getDataJob(token, id){
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
        if (response.ok){
            const data = await response.json();
            console.log("RECEIVED GET JOB   : " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);                    
            throw new Error("Erro ao getDados: " + JSON.stringify(errorData, null, 2));
        }       
    }
}