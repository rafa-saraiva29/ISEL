import time


class StartupController:
    def __init__(self, system_manager):
        self.system_manager = system_manager

    def start(self):
        start_time = time.time()
        self.system_manager.start()
        end_time = time.time()
        duration = end_time - start_time
        return {"message": f"Startup Successfull!\nInitial Processing took {duration:.2f}s."}, 200

