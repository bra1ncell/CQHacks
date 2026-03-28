import "./EventsView.css";

type Props = {
  onBack: () => void;
};

export default function EventsView({ onBack }: Props) {
  return (
    <div className="events-view">
      <button type="button" className="events-back" onClick={onBack}>
        ← Back to menu
      </button>
      <div className="events-card">
        <h1 className="events-title">Events</h1>
        <p className="events-lead">
          Event schedules and details will live here. Design-only placeholder for now.
        </p>
        <div className="events-placeholder">
          <span className="events-badge">Coming soon</span>
        </div>
      </div>
    </div>
  );
}
