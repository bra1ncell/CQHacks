import { useCallback, useEffect, useRef, useState } from "react";
import Aurora from "./components/Aurora.tsx";
import {
  HARDCODED_RACES,
  thumbGradientForRound,
  trackPhotoUrl,
  type RaceCard,
} from "./racesData.ts";
import "./RaceHubScreen.css";

const RACE_SELECTOR_AURORA_STOPS = ["#ec0004", "#dce5e9", "#f50600"] as const;

type Props = {
  /** Called after the fullscreen transition animation (session / next step). */
  onRaceConfirmed: (round: number) => void;
  onBackToIntro: () => void;
};

export default function RaceHubScreen({ onRaceConfirmed, onBackToIntro }: Props) {
  const [selected, setSelected] = useState(0);
  const [entered, setEntered] = useState(false);
  const [fsRace, setFsRace] = useState<RaceCard | null>(null);
  const [gridCols, setGridCols] = useState(5);
  const userHasClickedGrid = useRef(false);
  const cardRefs = useRef<(HTMLButtonElement | null)[]>([]);

  useEffect(() => {
    const mq = window.matchMedia("(max-width: 720px)");
    const sync = () => setGridCols(mq.matches ? 2 : 5);
    sync();
    mq.addEventListener("change", sync);
    return () => mq.removeEventListener("change", sync);
  }, []);

  useEffect(() => {
    const id = requestAnimationFrame(() => setEntered(true));
    return () => cancelAnimationFrame(id);
  }, []);

  const scrollCardIntoView = useCallback((i: number) => {
    cardRefs.current[i]?.scrollIntoView({
      behavior: "smooth",
      block: "nearest",
      inline: "nearest",
    });
  }, []);

  useEffect(() => {
    scrollCardIntoView(selected);
  }, [selected, scrollCardIntoView]);

  const beginTransition = useCallback((race: RaceCard) => {
    setFsRace(race);
  }, []);

  useEffect(() => {
    if (!fsRace) return;
    const round = fsRace.round;
    const t = window.setTimeout(() => {
      onRaceConfirmed(round);
    }, 780);
    return () => clearTimeout(t);
  }, [fsRace, onRaceConfirmed]);

  useEffect(() => {
    const max = HARDCODED_RACES.length - 1;
    const onKey = (e: KeyboardEvent) => {
      if (fsRace) {
        if (e.key === "Escape") {
          e.preventDefault();
          setFsRace(null);
        }
        return;
      }
      if (e.key === "ArrowRight") {
        e.preventDefault();
        setSelected((s) => Math.min(s + 1, max));
      } else if (e.key === "ArrowLeft") {
        e.preventDefault();
        setSelected((s) => Math.max(s - 1, 0));
      } else if (e.key === "ArrowDown") {
        e.preventDefault();
        setSelected((s) => Math.min(s + gridCols, max));
      } else if (e.key === "ArrowUp") {
        e.preventDefault();
        setSelected((s) => Math.max(s - gridCols, 0));
      } else if (e.key === "Enter") {
        e.preventDefault();
        beginTransition(HARDCODED_RACES[selected]);
      } else if (e.key === "Escape") {
        e.preventDefault();
        onBackToIntro();
      }
    };
    window.addEventListener("keydown", onKey);
    return () => window.removeEventListener("keydown", onKey);
  }, [selected, fsRace, gridCols, beginTransition, onBackToIntro]);

  const handleCardClick = (i: number) => {
    if (!userHasClickedGrid.current) {
      userHasClickedGrid.current = true;
      setSelected(i);
      return;
    }
    if (i === selected) {
      beginTransition(HARDCODED_RACES[i]);
    } else {
      setSelected(i);
    }
  };

  const race = HARDCODED_RACES[selected];

  return (
    <div className={`race-hub ${entered ? "race-hub--visible" : ""}`}>
      <div className="race-hub__aurora" aria-hidden>
        <Aurora
          colorStops={[...RACE_SELECTOR_AURORA_STOPS]}
          blend={1.08}
          amplitude={1.2}
          speed={2}
        />
      </div>
      <div className="race-hub__veil" aria-hidden />
      <div className="race-hub__stars" aria-hidden />

      <header className="race-hub__header">
        <div className="race-hub__title-block">
          <h1 className="race-hub__title">Race Selector</h1>
          <p className="race-hub__subtitle">Choose a race</p>
        </div>
      </header>

      <section className="race-hub__section" aria-label="Race selection">
        <div className="race-hub__grid-scroll">
          <div
            className="race-hub__grid"
            role="listbox"
            aria-label="Races"
            aria-activedescendant={`race-card-${race.round}`}
          >
            {HARDCODED_RACES.map((r: RaceCard, i) => {
              const isSel = i === selected;
              return (
                <button
                  key={r.round}
                  ref={(el) => {
                    cardRefs.current[i] = el;
                  }}
                  type="button"
                  role="option"
                  aria-selected={isSel}
                  id={`race-card-${r.round}`}
                  className={`race-card ${isSel ? "race-card--selected" : ""}`}
                  onClick={() => handleCardClick(i)}
                >
                  <div
                    className="race-card__thumb"
                    style={{ background: thumbGradientForRound(r.round) }}
                  >
                    <img
                      src={trackPhotoUrl(r.round)}
                      alt=""
                      className="race-card__thumb-photo"
                      loading="lazy"
                      decoding="async"
                      onError={(e) => e.currentTarget.remove()}
                    />
                  </div>
                  <div className="race-card__label">
                    <span className="race-card__name">{r.name}</span>
                    <span className="race-card__loc">{r.location}</span>
                  </div>
                </button>
              );
            })}
          </div>
        </div>
      </section>

      {fsRace && (
        <div
          className="race-hub__fs"
          role="dialog"
          aria-modal="true"
          aria-label={`Opening ${fsRace.name}`}
        >
          <div className="race-hub__fs-backdrop" aria-hidden />
          <div className="race-hub__fs-panel">
            <div
              className="race-hub__fs-thumb"
              style={{ background: thumbGradientForRound(fsRace.round) }}
            >
              <img
                src={trackPhotoUrl(fsRace.round)}
                alt=""
                className="race-hub__fs-thumb-photo"
                onError={(e) => e.currentTarget.remove()}
              />
            </div>
            <div className="race-hub__fs-meta">
              <h2 className="race-hub__fs-name">{fsRace.name}</h2>
              <p className="race-hub__fs-loc">{fsRace.location}</p>
            </div>
          </div>
        </div>
      )}

      <footer className="race-hub__footer">
        <button type="button" className="race-hub__pill" onClick={onBackToIntro}>
          <span className="race-hub__pill-arrow">←</span>
          <span>Back</span>
        </button>
        <button
          type="button"
          className="race-hub__pill race-hub__pill--primary"
          onClick={() => beginTransition(race)}
        >
          <span>OK</span>
          <span className="race-hub__pill-arrow">→</span>
        </button>
      </footer>
    </div>
  );
}
