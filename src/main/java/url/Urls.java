package url;

public interface Urls {
//    String demoBaseUrl = "https://demowebshop.tricentis.com";
    String demoBaseUrl = System.getProperty("baseUrl");
    String baseUrl = "https://the-internet.herokuapp.com";
    String dropdownSlug = "/dropdown";
    String loginSlug = "/login";
    String iframeSlug = "/iframe";
    String hoverSlug = "/hovers";
    String alertsSlug = "/javascript_alerts";
    String dynamicControlSlug = "/dynamic_controls";
    String javaScriptSlug = "/floating_menu";
}
