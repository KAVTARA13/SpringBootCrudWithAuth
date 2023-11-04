$(document).ready(function (){
    // $("#buttonAdd2Cart").on("click",function (e){
    //     // alert('Add to cart');
    //     addToCart();
    // });
});
function addToCart(productId){

    let quantity = $("#quantity" + productId).val();
        let url = contextPath+"addToCart/add/" + productId + "/" + quantity;

        $.ajax({
            type:"POST",
            url:url,
            beforeSend:function (xhr) {
                xhr.setRequestHeader(csrfHeaderName,csrfToken);
            }
        }).done(function (response) {
            alert(response);
        }).fail(function () {
            alert("error "+url);
        });

}