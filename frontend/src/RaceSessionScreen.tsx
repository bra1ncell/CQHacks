import { raceForRound } from "./racesData.ts";
import "./RaceSessionScreen.css";

type Props = {
  round: number;
  onBack: () => void;
  onOpenRaceLive: () => void;
};

export default function RaceSessionScreen({ round, onBack, onOpenRaceLive }: Props) {
  const race = raceForRound(round);

  return (
    <div className="race-session">
      <button type="button" className="race-session__back" onClick={onBack}>
        ← Back to Race Selector
      </button>

      <main className="race-session__main">
        <h1 className="race-session__title">{race?.name ?? "Grand Prix"}</h1>
        <p className="race-session__loc">{race?.location ?? ""}</p>
        <p className="race-session__placeholder">
          Barebones session view — hook up timing, standings, or stream here.
        </p>
        <button type="button" className="race-session__cta" onClick={onOpenRaceLive}>
          Open Race Live (data view)
        </button>
      </main>
    </div>
  );
}
