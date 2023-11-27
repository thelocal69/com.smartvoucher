# ADMIN PAGE OF VOUCHER APP

## Steps to build project

### Dowload nodejs v18.17.0

https://nodejs.org/dist/v18.17.0/
Dowload .msi option
=> install nodejs
Add nodejs to system enviroment variable

### Open project in code editor

### Open terminal in code editor

enter scripts below :

`copy .env.staging .env`
`npm install `
=> wait for install

after installation end => type `npm start` to start website

Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

### Start BE and enjoy

Note : BE need add this script to WebEcommerceSmartvoucherApplication for handling CORS

@Bean
public WebMvcConfigurer corsConfigurer() {
return new WebMvcConfigurer() {
@Override
public void addCorsMappings(CorsRegistry registry) {
registry.addMapping("/\*_").allowedOrigins("_").allowedMethods("_").allowedHeaders("_").allowCredentials(false).maxAge(3600);;
}
};
}
