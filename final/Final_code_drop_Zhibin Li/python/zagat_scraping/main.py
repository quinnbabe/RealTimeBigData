from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from bs4 import BeautifulSoup
import selenium
from selenium.webdriver.support import expected_conditions as EC

#This test aim at extract reviews from Zagat.com
#Since Zagat use javascript to generate more reviews
#The content of restaurants is dynamically loaded
#we have to use selenium and beautifulsoup to act like a real browser and generate the content we need
#following is the experience code, it generate the dynamic content, but not filter the content yet
from selenium.webdriver.support.wait import WebDriverWait
import time
import re, htmlentitydefs
from util import convert,parseres_name,unescape

def writetofile(res_data_list):
    f = open('zagat_dataset','w+')
    for d in res_data_list:
        if d['food'] == -1 or d['decor'] == -1 or d['service'] == -1 or d['cost'] == -1:
            pass
        else:
            avg = (d['food'] + d['decor'] + d['service'])/3.0/30*100.0
            s = d['name']+','+d['cuisine']+','+str(avg)+'\n'
            f.write(s.encode('utf8'))







firefox_profile = webdriver.FirefoxProfile()
# firefox_profile.set_preference('permissions.default.stylesheet', 2)
firefox_profile.set_preference('permissions.default.image', 2)
firefox_profile.set_preference('dom.ipc.plugins.enabled.libflashplayer.so', 'false')

driver = webdriver.Firefox(firefox_profile=firefox_profile)

url='http://www.zagat.com/p/new-york-city'
e = driver.get(url)
print driver.current_url
# driver.implicitly_wait(10)

for i in range(0,150):
    # elem = driver.find_element_by_xpath('//*[@id="content"]/div[2]/a')
    elem = driver.find_element_by_css_selector("#content > div.cases > a")
    while(not elem.is_displayed()):
        time.sleep(0.5)
    elem.click();
    print i

time.sleep(4)
# e = driver.find_element_by_class_name("js-search-results js-place-results js-lock-on-load ")
e = driver.find_element_by_css_selector(".js-search-results.js-place-results.js-lock-on-load")
e_text = e.get_attribute("innerHTML")

soup = BeautifulSoup(e_text)

res_data = {}
res_data["food"] = 0
res_data["decor"] = 0
res_data["service"] = 0
res_data["cost"] = 0

res_data_list=[]

e2 = soup.find_all("div",class_="case js-case")
# print e2[0]
# print e2[0].find("div",class_="image")
for elem in e2:
    t = elem.find("div",class_="text")
    res_name = t.find("div",class_="text-cnt Restaurants").a.text


    res_cui = unescape(t.find("div",class_="text-cnt Restaurants").p.text)
    # str.decode("utf-8").replace(res_cui, "@")
    special = u"\u2022"
    res_cui = res_cui.replace(special,'@')
    res_cui = parseres_name(res_cui)

    stats = t.select(".text-stats")
    res_data["name"] = res_name
    res_data["cuisine"] = res_cui
    res_data["food"] = convert(t.select(".i-number.i-number-red")[0].text)
    res_data["decor"] = convert(t.select(".i-number")[0].text)
    res_data["service"] = convert(t.select(".i-number")[1].text)
    res_data["cost"] = convert(t.select(".i-number")[2].text)
    print res_data
    newd = res_data.copy()
    res_data_list.append(newd)

print res_data_list
print len(res_data_list)

writetofile(res_data_list)
# st2r = "".expandtabs()

#

driver.quit()



