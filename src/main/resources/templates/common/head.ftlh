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
<style>
html,
body {
    width: 100%;
    height: inherit !important;
    overflow: visible;
    overflow-x: hidden;
}

::-webkit-scrollbar {
    width: 7px;
    height: 8px;
}

::-webkit-scrollbar-track {
    background-color: rgba(0, 0, 0, 0.05);
}

::-webkit-scrollbar-thumb {
    background-color: rgba(144, 147, 153, 0.3);
    border-radius: 2px;
    box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.2);
}

::-webkit-scrollbar-thumb:hover {
    background-color: #b6b7b9;
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
  const axiosInstance = axios.create({
    headers: { 'Content-Type': 'application/json;charset=UTF-8' },
    timeout: 10 * 1000,
    validateStatus: status => status >= 200 && status <= 500
  });
</script>
