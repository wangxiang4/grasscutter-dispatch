<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
<link rel="icon" href="/image/favicon.ico">
<link href="/css/antd.min.css" rel="stylesheet">
<script src="/js/vue.global.min.js"></script>
<script src="/js/dayjs.min.js"></script>
<script src="/js/customParseFormat.min.js"></script>
<script src="/js/weekday.min.js"></script>
<script src="/js/localeData.min.js"></script>
<script src="/js/weekOfYear.min.js"></script>
<script src="/js/weekYear.min.js"></script>
<script src="/js/advancedFormat.min.js"></script>
<script src="/js/quarterOfYear.min.js"></script>
<script src="/js/antd.min.js"></script>
<script src="/js/axios.min.js"></script>
<script src="/js/tailwindcss.min.js"></script>
<script src="/js/fontawesome.min.js"></script>
<style>
html,
body {
    overflow-y: auto;
    overflow-x: hidden;
}

::-webkit-scrollbar {
    display: none;
}
</style>
<script>
  tailwind.config = {
    corePlugins: {
      preflight: false,
    }
  }
</script>
<script>
  window.onload=function () {
    // handle prevent double click zoom
    document.addEventListener('touchstart',function (event) {
      if(event.touches.length>1){
        event.preventDefault()
      }
    })
    let lastTouchEnd = 0
    document.addEventListener('touchend',function (event) {
      const now = (new Date()).getTime()
      if(now-lastTouchEnd<=300){
        event.preventDefault()
      }
      lastTouchEnd = now
    })
    // handle prevent two finger zoom
    document.addEventListener('gesturestart', (e) => e.preventDefault())
  }
  const axiosInstance = axios.create({
    headers: { 'Content-Type': 'application/json;charset=UTF-8' },
    timeout: 10 * 1000,
    validateStatus: status => status >= 200 && status <= 500
  })
</script>
