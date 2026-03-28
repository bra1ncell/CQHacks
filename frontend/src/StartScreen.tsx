import { useCallback, useEffect, useState } from "react";
import "./StartScreen.css";

export type StartDestination = "race-live";

const INTRO_TOTAL_MS = 5000;
const INTRO_FADE_MS = 800;

type Props = {
  onNavigate: (dest: StartDestination) => void;
  /** Fires after intro duration (with fade). Skipped if user picks a menu item first. */
  onIntroComplete: () => void;
};

const OPTIONS: { id: StartDestination; label: string }[] = [{ id: "race-live", label: "RACE LIVE" }];

/** Files live in frontend/public/asset/images/ — Vite serves them at BASE_URL + asset/images/... */
const SLIDE_FILES = ["1.jpg", "2.jpg", "3.jpg"] as const;

function publicImageUrl(file: string): string {
  const base = import.meta.env.BASE_URL;
  const prefix = base.endsWith("/") ? base : `${base}/`;
  return `${prefix}asset/images/${file}`;
}

const SLIDE_INTERVAL_MS = 1500;
const SLIDE_FADE_MS = 450;

function IconRaceLive() {
  return (
    <svg className="mk-icon-svg" viewBox="0 0 48 48" aria-hidden>
      <circle cx="24" cy="24" r="18" fill="#37474f" stroke="#ff5252" strokeWidth="2" />
      <circle cx="24" cy="24" r="14" fill="#263238" />
      <path
        d="M24 14 L24 24 L32 28"
        fill="none"
        stroke="#ff5252"
        strokeWidth="2.5"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
      <circle cx="24" cy="24" r="2.5" fill="#ff5252" />
    </svg>
  );
}

export default function StartScreen({ onNavigate, onIntroComplete }: Props) {
  const [index, setIndex] = useState(0);
  const [slide, setSlide] = useState(0);
  const [exiting, setExiting] = useState(false);

  const confirm = useCallback(() => {
    onNavigate(OPTIONS[index].id);
  }, [index, onNavigate]);

  useEffect(() => {
    const id = window.setInterval(() => {
      setSlide((s) => (s + 1) % SLIDE_FILES.length);
    }, SLIDE_INTERVAL_MS);
    return () => window.clearInterval(id);
  }, []);

  useEffect(() => {
    const fadeAt = Math.max(0, INTRO_TOTAL_MS - INTRO_FADE_MS);
    const fadeTimer = window.setTimeout(() => setExiting(true), fadeAt);
    const doneTimer = window.setTimeout(() => onIntroComplete(), INTRO_TOTAL_MS);
    return () => {
      window.clearTimeout(fadeTimer);
      window.clearTimeout(doneTimer);
    };
  }, [onIntroComplete]);

  useEffect(() => {
    const onKey = (e: KeyboardEvent) => {
      if (e.key === "ArrowDown" || e.key === "s" || e.key === "S") {
        e.preventDefault();
        setIndex((i) => Math.min(i + 1, OPTIONS.length - 1));
      } else if (e.key === "ArrowUp" || e.key === "w" || e.key === "W") {
        e.preventDefault();
        setIndex((i) => Math.max(i - 1, 0));
      } else if (e.key === "Enter" || e.key === " ") {
        e.preventDefault();
        confirm();
      }
    };
    window.addEventListener("keydown", onKey);
    return () => window.removeEventListener("keydown", onKey);
  }, [confirm]);

  return (
    <div className={`start-screen ${exiting ? "start-screen--exiting" : ""}`}>
      <div className="start-screen-media" aria-hidden>
        <div className="start-art-slides">
          {SLIDE_FILES.map((file, i) => (
            <img
              key={file}
              src={publicImageUrl(file)}
              alt=""
              loading={i === 0 ? "eager" : "lazy"}
              decoding="async"
              className={`start-art-slide ${i === slide ? "start-art-slide--visible" : ""}`}
              style={{ transitionDuration: `${SLIDE_FADE_MS}ms` }}
            />
          ))}
        </div>
        <div className="start-screen-media-overlay" />
      </div>

      <div className="start-layout">
        <div className="start-menu-panel">
          <p className="start-brand">CQHacks</p>
          <nav className="start-nav" aria-label="Main menu">
            {OPTIONS.map((opt, i) => {
              const selected = i === index;
              return (
                <button
                  key={opt.id}
                  type="button"
                  className={`mk-menu-btn ${selected ? "mk-menu-btn--selected" : ""}`}
                  onClick={() => {
                    setIndex(i);
                    onNavigate(opt.id);
                  }}
                  onMouseEnter={() => setIndex(i)}
                  onFocus={() => setIndex(i)}
                >
                  <span className="mk-menu-btn__frame" aria-hidden>
                    <IconRaceLive />
                  </span>
                  <span className="mk-menu-btn__track">
                    <span className="mk-menu-btn__label">{opt.label}</span>
                    {selected && <span className="mk-menu-btn__chevrons" aria-hidden />}
                  </span>
                </button>
              );
            })}
          </nav>
          <p className="start-hint">Enter to open Race Live · or wait 5s for Race Selector</p>
        </div>

        <div className="start-art-column" />
      </div>
    </div>
  );
}
