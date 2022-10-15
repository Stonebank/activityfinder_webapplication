navigator.geolocation.getCurrentPosition(pos => {
    console.log(pos.coords.latitude + " " + pos.coords.longitude)
})