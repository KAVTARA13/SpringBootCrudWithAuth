$(document).ready(function () {

        $(".minusButton").on("click",function (evt){
            evt.preventDefault();
            decreaseQuantity($(this));
        });

        $(".plusButton").on("click", function (evt){
            evt.preventDefault();
            increaseQuantity($(this));
        });
        $(".link-remove").on("click",function (evt) {
            evt.preventDefault();
            removeFromCart($(this));
        });
    updateTotal();
});
function removeFromCart(link){
    let url = link.attr("href");

    $.ajax({
        type:"POST",
        url:url,
        beforeSend:function (xhr) {
            xhr.setRequestHeader(csrfHeaderName,csrfToken);
        }
    }).done(function (response) {
        alert(response);
        location.reload();
    }).fail(function () {
        alert("error "+url);
    });
}
function decreaseQuantity(link) {
    let productId = link.attr("pid");
    let qtyInput = $("#quantity" + productId);

    let newQty = parseInt(qtyInput.val()) - 1;
    if (newQty>0) {
        qtyInput.val(newQty);
        updateQuantity(productId,newQty);
    };
}
function increaseQuantity(link) {
    let productId = link.attr("pid");
    let qtyInput = $("#quantity" + productId);

    let newQty = parseInt(qtyInput.val()) + 1;
    if (newQty>0) {
        qtyInput.val(newQty);
        updateQuantity(productId,newQty);
    };
}
function updateQuantity(productId,quantity) {
    let url = contextPath+"addToCart/update/" + productId + "/" + quantity;

    $.ajax({
        type:"POST",
        url:url,
        beforeSend:function (xhr) {
            xhr.setRequestHeader(csrfHeaderName,csrfToken);
        }
    }).done(function (newSubtotal) {
        updateSubtotal(newSubtotal,productId);
        updateTotal();
    }).fail(function () {
        alert("error "+url);
    });
}
function updateSubtotal(newSubtotal,productId) {
    $("#subtotal"+productId).text(newSubtotal);
}
function updateTotal() {
    let total = 0.0;
    
    $(".productSubTotal").each(function (index,element) {
       total = total+parseFloat(element.innerHTML);
    });
    console.log(total);
    $("#totalAmount").text(total);
}