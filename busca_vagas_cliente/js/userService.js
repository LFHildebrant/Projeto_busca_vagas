import { Config } from "./main.js";

export class UserService {
    constructor(){

    }

    async getAllDataUser(token, id){
        console.log("📤 ENVIANDO requisição GET para:" + Config.BASE_URL + "/users" + "/" + id);
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/users" + "/" + id, {
                method: "GET",
                    headers: {
                        "Content-type": "application/json",
                        "Authorization": `Bearer ${token}`
                    },
        });
        if (response.ok){
            const data = await response.json();
            console.log("RECEIVED GET USER: " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);                    
            throw new Error("Erro ao getDados: " + JSON.stringify(errorData, null, 2));
        }       
    }

    async deleteUSer(id, token){
        console.log("📤 ENVIANDO requisição DELETE para:" + sessionStorage.getItem("BASE_URL") + "/users" + "/" + id);
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/users" + "/" + id,{
            method: "DELETE",
            headers: {
                    "Content-type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
        });
        if (response.ok){
            const data = await response.json();
            console.log("RECEIVED DELETE USER: " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);                    
            throw new Error("Erro ao deletar: " + JSON.stringify(errorData, null, 2));
        }    
    }

    async createUser(dataUser) {
        console.log("📤 ENVIANDO requisição POST para:" + sessionStorage.getItem("BASE_URL") + "/users");
        console.log("📦 Corpo da requisição:", JSON.stringify(dataUser, null, 2));
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/users", {
            method: "POST",
                body: JSON.stringify(dataUser),
                headers: {
                    "Content-type": "application/json",
                },
            });
        if (response.ok){
            const data = await response.json();
            console.log("RECEIVED CREATE USER JSON: " + JSON.stringify(data, null, 2));
            return data;
        } else {
            const errorData = await response.json().catch(() => null);                    
            throw new Error("Erro no create: " + JSON.stringify(errorData, null, 2));
        }               
    }

    async updateUser(id, token, dataUser) {

        console.log("📤 ENVIANDO requisição PATCH para:" + sessionStorage.getItem("BASE_URL") + "/users" + "/" + id);
        console.log("📦 Corpo da requisição:", JSON.stringify(dataUser, null, 2));
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });

        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/users" + "/" + id, {
            method: "PATCH",
                body: JSON.stringify(dataUser),
                headers: {
                    "Content-type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
            });
        if (response.ok){
            const data = await response.json();
            console.log("RECEIVED UPDATE USER: " + JSON.stringify(data, null, 2))
            return data;
        } else {
            const errorData = await response.json().catch(() => null);                    
            throw new Error("Erro no update: " + JSON.stringify(errorData, null, 2));
        }               
    }
}