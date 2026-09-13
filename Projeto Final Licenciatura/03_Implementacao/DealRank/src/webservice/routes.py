from flask import render_template, request, jsonify

def setup_routes(app, system_controller, startup_controller):

    @app.route('/')
    def home():
        return render_template("interface.html")
    
    @app.route("/deal", methods=["POST"])
    def add_deal():
        deal_data = request.get_json()
        response, status = system_controller.add_deal(deal_data)
        return jsonify(response), status

    @app.route("/deal", methods=["PUT"])
    def update_deal():
        deal_data = request.get_json()
        response, status = system_controller.update_deal(deal_data)
        return jsonify(response), status

    @app.route("/deal/<deal_id>", methods=["DELETE"])
    def remove_deal(deal_id):
        response, status = system_controller.remove_deal(deal_id)
        return jsonify(response), status

    @app.route("/deal/rank/<creator_id>", methods=["GET"])
    def get_deal_rank(creator_id):
        response, status = system_controller.get_deal_rank(creator_id)
        return jsonify(response), status

    @app.route("/api/startup", methods=["POST"])
    def startup():
        response, status = startup_controller.start()
        return jsonify(response), status
