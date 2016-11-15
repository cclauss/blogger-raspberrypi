class HelloWorld(object):

    def __init__(self, message):
        self.message = message

    def sayHello(self) -> object:
        print(self.message)

    def setMessage(self, message):
        self.message = message

    def getMessage(self):
        return self.message