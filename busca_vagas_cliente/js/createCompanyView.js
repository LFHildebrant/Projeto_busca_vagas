import { CompanyService } from "./companyService.js";

class CreateUserView{
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

        this.userForm.addEventListener("submit", this.handleSubmitUpdate.bind(this));
    }

    async handleSubmitUpdate(event){
        event.preventDefault();
        const companyService = new CompanyService();
        const fields = ["name", "username", "password", "phone", "email", "business", "street", "number", "city", "state"];

        const user = {};

        fields.forEach(field => {
            const value = document.getElementById(field).value.trim();
            if (value !== "") {
                user[field] = value;
            }
        });

        try{
            const result = await companyService.createCompany(user);

            this.message.style.color = "green";
            this.message.textContent = "Sucesso! -> " + result.message + " REDIRECIONANDO....";         
            this.message.classList.remove("hide"); 
            
            setTimeout(() => {
                    window.location.replace("index.html");
                }, 2000)
        } catch (e){
            console.log(e);
            if (e.message) {
            console.log("Detalhes:", e.response);
            }
        }
    } 

}

new CreateUserView();