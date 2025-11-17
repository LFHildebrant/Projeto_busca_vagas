import { Config } from "./main.js";

export class CompanyService {
    constructor(){

    }

    async getAllDataCompany(token, id){
        console.log("📤 ENVIANDO requisição GET para:" + sessionStorage.getItem("BASE_URL") + "/companies" + "/" + id);
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/companies" + "/" + id, {
                method: "GET",
                    headers: {
                        "Content-type": "application/json",
                        "Authorization": `Bearer ${token}`
                    },
        });
        if (response.ok){
            const data = await response.json();
            console.log("RECEIVED GET COMPANY   : " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);                    
            throw new Error("Erro ao getDados: " + JSON.stringify(errorData, null, 2));
        }       
    }

    async deleteCompany(id, token){
        console.log("📤 ENVIANDO requisição DELETE para:" + sessionStorage.getItem("BASE_URL") + "/companies" + "/" + id);
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/companies" + "/" + id,{
            method: "DELETE",
            headers: {
                    "Content-type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
        });
        if (response.ok){
            const data = await response.json();
            console.log("RECEIVED DELETE COMPANY: " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);                    
            throw new Error("Erro ao deletar: " + JSON.stringify(errorData, null, 2));
        }    
    }

    async createCompany(dataCompany) {
        console.log("📤 ENVIANDO requisição POST para:" + sessionStorage.getItem("BASE_URL") + "/companies");
        console.log("📦 Corpo da requisição:", JSON.stringify(dataCompany, null, 2));
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/companies", {
            method: "POST",
                body: JSON.stringify(dataCompany),
                headers: {
                    "Content-type": "application/json",
                },
            });
        if (response.ok){
            const data = await response.json();
            console.log("RECEIVED CREATE COMPANY JSON: " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);                    
            throw new Error("Erro no create: " + JSON.stringify(errorData, null, 2));
        }               
    }

    async updateCompany(id, token, dataCompany) {

        console.log("📤 ENVIANDO requisição PATCH para:" + sessionStorage.getItem("BASE_URL") + "/companies" + "/" + id);
        console.log("📦 Corpo da requisição:", JSON.stringify(dataCompany, null, 2));
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });

        const response = await fetch(sessionStorage.getItem("BASE_URL")+ "/companies" + "/" + id, {
            method: "PATCH",
                body: JSON.stringify(dataCompany),
                headers: {
                    "Content-type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
            });
        if (response.ok){
            const data = await response.json();
            console.log("RECEIVED UPDATE COMPANY: " + JSON.stringify(data, null, 2))
            return data;
        } else {
            const errorData = await response.json().catch(() => null);                    
            throw new Error("Erro no update: " + JSON.stringify(errorData, null, 2));
        }               
    }
}