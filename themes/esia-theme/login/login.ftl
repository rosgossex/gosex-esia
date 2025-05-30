<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('username','password') displayInfo=realm.password && realm.registrationAllowed && !registrationDisabled??; section>

<#if section = "header">
    <#-- We are using a custom logo div inside the form area -->
    <div
    id="kc-header"/>
<#elseif section = "form">
<div
    id="kc-form"
    <#if realm.password && realm.registrationAllowed && !registrationDisabled??>
    class="login-pf-pageH"
    <#else>
    class="login-pf-pageH"
    </#if>
>
    <div class="logo"></div>

    <div class="user"></div>

    <form id="kc-form-login" onsubmit="login.disabled = true; return true;" action="${url.loginAction}" method="post">
        <div class="kc-form-group ${messagesPerField.printIfExists('username',properties.kcFormGroupErrorClass!)}">
            <input tabindex="1" id="username" class="${properties.kcInputClass!}" name="username"
                   value="${(login.username!'')}" type="text"
                   placeholder="${msg('usernamePlaceholder')}"
                   aria-invalid="${messagesPerField.existsError('username','password')?c}"/>
        </div>

        <div class="kc-form-group ${messagesPerField.printIfExists('password',properties.kcFormGroupErrorClass!) }">
            <input tabindex="2" id="password" class="${properties.kcInputClass!}" name="password" type="password"
                   autocomplete="off"
                   placeholder="${msg('passwordPlaceholder')}"
                   aria-invalid="${messagesPerField.existsError('username','password')?c}"/>
        </div>

        <div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
            <input tabindex="4"
                   class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}"
                   name="login" id="kc-login" type="submit" value="${msg("doLogIn")}"/>
        </div>
    </form>
</div>
<#elseif section = "info" >
</#if>
</@layout.registrationLayout>
