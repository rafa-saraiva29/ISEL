from flask import Flask
from model.system_manager import SystemManager
from webservice.controllers.system_controller import SystemController
from webservice.controllers.startup_controller import StartupController
from webservice.routes import setup_routes

def create_app():
    app = Flask(__name__)

    system_manager = SystemManager()
    system_controller = SystemController(system_manager)
    startup_controller = StartupController(system_manager)

    setup_routes(app, system_controller, startup_controller)

    return app

if __name__ == "__main__":
    app = create_app()
    app.run(debug=True)
