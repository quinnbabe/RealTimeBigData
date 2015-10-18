import htmlentitydefs

__author__ = 'Benson'
# add some utility methods to print targeted element

def printTitle(text):
    print ''
    print '*' * len(text)
    print str.upper(text)
    print '*' * len(text)
    print ''


def printSubTitle(text):
    print ''
    print str.upper(text)
    print '=' * len(text)
    print ''


def printExplain(text):
    print ''
    print text
    print '-' * len(text)
    print ''


def printEqual(left, compare, rigth):
    print left, compare, rigth


def printTab(text):
    print '\t', text

news = {}
def processNews(listOfli):
    for new in listOfli:
        for category in new.find_all('a'):
            title = category.get('title')
            #If don't have title is because is a external link
            #to the source of the new. I discard this...
            if title is None:
                continue

            if title in news:
                news[title].append(new.text)
            else:
                news[title] = [new.text]


def filterTable(table):
    classes = table.get('class')

    if classes:
        return 'vevent' in classes
    else:
        return False


##
# Removes HTML or XML character references and entities from a text string.
#
# @param text The HTML (or XML) source text.
# @return The plain text, as a Unicode string, if necessary.
def unescape(text):
    def fixup(m):
        text = m.group(0)
        if text[:2] == "&#":
            # character reference
            try:
                if text[:3] == "&#x":
                    return unichr(int(text[3:-1], 16))
                else:
                    return unichr(int(text[2:-1]))
            except ValueError:
                pass
        else:
            # named entity
            try:
                text = unichr(htmlentitydefs.name2codepoint[text[1:-1]])
            except KeyError:
                pass
        return text # leave as is
    return re.sub("&#?\w+;", fixup, text)


def convert(scorestring):
    if scorestring=='-':
        return -1
    else:
        return float(scorestring)

def parseres_name(name):
    return name.split('@')[0]