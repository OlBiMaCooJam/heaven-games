const path = require("path");

module.exports = {
  "transpileDependencies": [
    "vuetify"
  ],
  outputDir: path.resolve(__dirname, "../../src/main/resources/static"),
  devServer: {
    proxy: {
      '/': {
        target: 'http://localhost:9000',
        ws: true,
        changeOrigin: true
      },
    },
  },
};