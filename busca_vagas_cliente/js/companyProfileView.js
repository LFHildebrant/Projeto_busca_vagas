import { Config } from './main.js';
import {CompanyService} from './companyService.js';

class UserProfileView{
    constructor(){
        this.message = document.getElementById("message");
        this.profile = document.getElementById("profile");
        this.inputName = document.getElementById("name");
        this.inputUsername = document.getElementById("username");
        this.inputPassword = document.getElementById("password");
        this.inputEmail = document.getElementById("email");
        this.inputPhone = document.getElementById("phone");
        this.inputBusiness = document.getElementById("business");
        this.inputStreet = document.getElementById("street");
        this.inputNumber = document.getElementById("number");
        this.inputCity = document.getElementById("city");
        this.inputState = document.getElementById("state");
        this.buttonSubmit = document.getElementById("buttonSubmit");
        this.userForm = document.getElementById("userForm");
        this.buttonDelete = document.getElementById("buttonDelete");

        this.fillPlaceholders();

        this.userForm.addEventListener("submit", this.handleSubmitUpdate.bind(this));

        this.buttonDelete.addEventListener("click", this.handleSubmitDelete.bind(this));

        this.message.classList.add("hide");
        this.profile.classList.remove("hide");
    }
    
    async handleSubmitUpdate(event){
        event.preventDefault();
        const companyService = new CompanyService();
        let id = Config.getId(localStorage.getItem("token"));
        const fields = ["name", "username", "password", "phone", "email", "business", "street", "number", "city", "state"];
        const user = {};

        fields.forEach(field => {
            const value = document.getElementById(field).value.trim();
            user[field] = value;
        });
        try{
            const result = await companyService.updateCompany(id, localStorage.getItem("token"), user);
            
            this.message.style.color = "green";
            this.message.textContent = "Sucesso! -> " + result.message;         
            this.message.classList.remove("hide"); 
            
            setTimeout(() => {
                    this.message.classList.add("hide");
                }, 9000)
           
        } catch (e){
            console.log(e);
            if (e.message) {
            console.log("Detalhes:", e.response);
            }
        }
    }

    async handleSubmitDelete(event){
        event.preventDefault();
        const companyService = new CompanyService();
        let id = Config.getId(localStorage.getItem("token"));
        try{
            const result = await companyService.deleteCompany(id, localStorage.getItem("token"));

            this.message.style.color = "green";
            this.message.textContent = "Sucesso! -> " + result.message;         
            this.message.classList.remove("hide");     

            setTimeout(() => {
                    window.location.replace("index.html");
                }, 1000)

        } catch (e){
            console.log(e);
            if (e.message) {
            console.log("Detalhes:", e.response);
            }
        }
    }

    async fillPlaceholders(){
        const companyService = new CompanyService();
        try{
            const dataCompany = await companyService.getAllDataCompany(localStorage.getItem("token"), localStorage.getItem("id"));

            this.inputName.value = dataCompany.name;
            this.inputUsername.value = dataCompany.username;
            this.inputPassword.password = dataCompany.password;
            this.inputEmail.value = dataCompany.email;
            this.inputPhone.value = dataCompany.phone;
            this.inputBusiness.value = dataCompany.business;
            this.inputStreet.value = dataCompany.street;
            this.inputNumber.value = dataCompany.number;
            this.inputCity.value = dataCompany.city;
            this.inputState.value = dataCompany.state;
            this.inputUsername.classList.add("disable");
            
        } catch (e){
            console.log(e);
        }
        
    }
}

new UserProfileView();