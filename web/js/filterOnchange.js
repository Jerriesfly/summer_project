let select_country = document.getElementById("country");
let select_city = document.getElementById("city");
select_country.onchange = function()
{
    let index = select_country.options[select_country.selectedIndex].value;

    for(let k = 1; k < select_city.length; k++)
    {
        select_city.removeChild(select_city.children[k--]);
    }

    for(let i in city[index])
    {
        let option_city = document.createElement('option');
        option_city.innerHTML = city[index][i];
        option_city.setAttribute('value', city[index][i]);
        select_city.appendChild(option_city);
    }
};