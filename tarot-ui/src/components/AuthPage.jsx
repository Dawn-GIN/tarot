import { useState } from 'react';
import { login, register, resetPassword, sendCode, saveAuth } from '../api/auth';
import styles from './AuthPage.module.css';

const MODES = { login: '登录', register: '注册', reset: '重置密码' };

export default function AuthPage({ onAuthenticated }) {
  const [mode, setMode] = useState('login');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [code, setCode] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [countdown, setCountdown] = useState(0);

  const switchMode = (next) => { setMode(next); setCode(''); setError(''); };
  const requestCode = async () => {
    setError(''); setLoading(true);
    try {
      await sendCode(email, mode === 'register' ? 'REGISTER' : 'RESET_PASSWORD');
      setCountdown(60);
      const timer = window.setInterval(() => setCountdown((n) => { if (n <= 1) { clearInterval(timer); return 0; } return n - 1; }), 1000);
    } catch (e) { setError(e.message); } finally { setLoading(false); }
  };
  const submit = async (event) => {
    event.preventDefault(); setError(''); setLoading(true);
    try {
      if (mode === 'login') {
        const auth = await login(email, password); saveAuth(auth); onAuthenticated(auth);
      } else if (mode === 'register') {
        const auth = await register(email, password, code); saveAuth(auth); onAuthenticated(auth);
      } else {
        await resetPassword(email, password, code); switchMode('login'); setError('密码已重置，请使用新密码登录');
      }
    } catch (e) { setError(e.message || '操作失败，请稍后再试'); } finally { setLoading(false); }
  };
  const requiresCode = mode !== 'login';
  return <main className={styles.page}>
    <section className={styles.panel}>
      <div className={styles.brand}><span>✦</span><h1>星语塔罗</h1><p>听见命运的低语</p></div>
      <div className={styles.tabs}>{Object.entries(MODES).map(([key, label]) => <button key={key} type="button" onClick={() => switchMode(key)} className={mode === key ? styles.active : ''}>{label}</button>)}</div>
      <form onSubmit={submit} className={styles.form}>
        <label>邮箱<input value={email} onChange={(e) => setEmail(e.target.value)} type="email" autoComplete="email" placeholder="name@example.com" required /></label>
        <label>密码<input value={password} onChange={(e) => setPassword(e.target.value)} type="password" autoComplete={mode === 'login' ? 'current-password' : 'new-password'} placeholder="8 至 72 位" required /></label>
        {requiresCode && <label>邮箱验证码<div className={styles.codeRow}><input value={code} onChange={(e) => setCode(e.target.value)} inputMode="numeric" maxLength="6" placeholder="6 位验证码" required /><button type="button" disabled={loading || countdown > 0 || !email} onClick={requestCode}>{countdown ? `${countdown}s 后重发` : '获取验证码'}</button></div></label>}
        {error && <p className={styles.error}>{error}</p>}
        <button className={styles.submit} disabled={loading}>{loading ? '请稍候…' : mode === 'login' ? '进入占卜' : mode === 'register' ? '创建账号' : '重置密码'}</button>
      </form>
    </section>
  </main>;
}
