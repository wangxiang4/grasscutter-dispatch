<link rel="icon" href="/image/favicon.ico">
<script src="/js/axios.min.js"></script>
<script src="/js/tailwindcss.min.js"></script>
<script src="/js/vue.global.min.js"></script>
<script>
  const axiosInstance = axios.create({
    headers: { 'Content-Type': 'application/json;charset=UTF-8' },
    timeout: 10 * 1000,
    validateStatus: status => status >= 200 && status <= 500
  });
</script>
