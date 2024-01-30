        document.addEventListener('DOMContentLoaded', function(){

            if(document.querySelector('.darkmode')){
                if(localStorage.getItem("darkmode") == 'on'){

                    document.body.dataset.darkmode='on';
                    document.querySelector('#toggle-radio-dark').checked = true;
                }

                document.querySelector('.darkmode').addEventListener("click", e=>{
                    if(e.target.classList.contains('todark')){
                        document.body.dataset.darkmode='on';
                        localStorage.setItem("darkmode", "on");
                    }else if(e.target.classList.contains('tolight')){
                        document.body.dataset.darkmode='off';
                        localStorage.setItem("darkmode", "off");
                    }
                },false);
            }else{
                localStorage.removeItem("darkmode");
            }

        })